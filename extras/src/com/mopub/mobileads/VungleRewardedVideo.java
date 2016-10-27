package com.skillz.mopub.mobileads;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.skillz.mopub.common.BaseLifecycleListener;
import com.skillz.mopub.common.DataKeys;
import com.skillz.mopub.common.LifecycleListener;
import com.skillz.mopub.common.MediationSettings;
import com.skillz.mopub.common.MoPubReward;
import com.skillz.mopub.common.logging.MoPubLog;
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.EventListener;
import com.vungle.publisher.VunglePub;

import java.util.*;
import java.util.concurrent.*;

/**
 * A custom event for showing Vungle rewarded videos.
 *
 * Certified with Vungle 3.3.0
 */
public class VungleRewardedVideo extends CustomEventRewardedVideo {

    private static final String DEFAULT_VUNGLE_APP_ID = "YOUR_DEFAULT_VUNGLE_APP_ID";

    /*
     * These constants are intended for MoPub internal use. Do not modify.
     */
    public static final String APP_ID_KEY = "appId";
    public static final String VUNGLE_AD_NETWORK_CONSTANT = "vngl_id";

    // This has to be reinitialized every time the CE loads to avoid conflict with the interstitials.
    private static VunglePub sVunglePub;
    private static VungleRewardedVideoListener sVungleListener;
    private static boolean sInitialized;
    private static final LifecycleListener sLifecycleListener = new BaseLifecycleListener() {
        @Override
        public void onPause(@NonNull final Activity activity) {
            super.onPause(activity);
            sVunglePub.onPause();
        }

        @Override
        public void onResume(@NonNull final Activity activity) {
            super.onResume(activity);
            sVunglePub.onResume();
        }
    };

    private final Handler mHandler;
    private boolean mIsLoading;
    private String mAdUnitId;

    public VungleRewardedVideo() {
        sVungleListener = new VungleRewardedVideoListener();
        mHandler = new Handler(Looper.getMainLooper());
        mIsLoading = false;
    }

    @Nullable
    @Override
    public CustomEventRewardedVideoListener getVideoListenerForSdk() {
        return sVungleListener;
    }

    @Nullable
    @Override
    public LifecycleListener getLifecycleListener() {
        return sLifecycleListener;
    }

    @NonNull
    @Override
    protected String getAdNetworkId() {
        return VUNGLE_AD_NETWORK_CONSTANT;
    }

    @Override
    protected boolean checkAndInitializeSdk(@NonNull final Activity launcherActivity,
                @NonNull final Map<String, Object> localExtras,
                @NonNull final Map<String, String> serverExtras) throws Exception {
        synchronized (VungleRewardedVideo.class) {
            if (!sInitialized) {
                sVunglePub = VunglePub.getInstance();
                sInitialized = true;
                return true;
            }
            return false;
        }
    }

    @Override
    protected void loadWithSdkInitialized(@NonNull final Activity activity, @NonNull final Map<String, Object> localExtras, @NonNull final Map<String, String> serverExtras) throws Exception {
        String appId = serverExtras.containsKey(APP_ID_KEY) ? serverExtras.get(APP_ID_KEY) : DEFAULT_VUNGLE_APP_ID;
        sVunglePub.init(activity, appId);
        sVunglePub.setEventListeners(sVungleListener);
        Object adUnitObject = localExtras.get(DataKeys.AD_UNIT_ID_KEY);
        if (adUnitObject instanceof String) {
            mAdUnitId = (String) adUnitObject;
        }
        if (sVunglePub.isAdPlayable()) {
            notifyAdAvailable();
        } else {
            mIsLoading = true;
        }
    }

    @Override
    protected boolean hasVideoAvailable() {
        return sVunglePub.isAdPlayable();
    }

    @Override
    protected void showVideo() {
        final AdConfig adConfig = new AdConfig();
        adConfig.setIncentivized(true);
        setUpMediationSettingsForRequest(adConfig);
        sVunglePub.playAd(adConfig);
    }

    private void setUpMediationSettingsForRequest(AdConfig adConfig) {
        final VungleMediationSettings globalMediationSettings =
                MoPubRewardedVideoManager.getGlobalMediationSettings(VungleMediationSettings.class);
        final VungleMediationSettings instanceMediationSettings =
                MoPubRewardedVideoManager.getInstanceMediationSettings(VungleMediationSettings.class, mAdUnitId);

        // Local options override global options.
        // The two objects are not merged.
        if (instanceMediationSettings != null) {
            modifyAdConfig(adConfig, instanceMediationSettings);
        } else if (globalMediationSettings != null) {
            modifyAdConfig(adConfig, globalMediationSettings);
        }
    }

    private void modifyAdConfig(AdConfig adConfig, VungleMediationSettings mediationSettings) {
        if (!TextUtils.isEmpty(mediationSettings.body)) {
            adConfig.setIncentivizedCancelDialogBodyText(mediationSettings.body);
        }
        if (!TextUtils.isEmpty(mediationSettings.closeButtonText)) {
            adConfig.setIncentivizedCancelDialogCloseButtonText(mediationSettings.closeButtonText);
        }
        if (!TextUtils.isEmpty(mediationSettings.keepWatchingButtonText)) {
            adConfig.setIncentivizedCancelDialogKeepWatchingButtonText(mediationSettings.keepWatchingButtonText);
        }
        if (!TextUtils.isEmpty(mediationSettings.title)) {
            adConfig.setIncentivizedCancelDialogTitle(mediationSettings.title);
        }
        if (!TextUtils.isEmpty(mediationSettings.userId)) {
            adConfig.setIncentivizedUserId(mediationSettings.userId);
        }
    }

    private void notifyAdAvailable() {
        MoPubLog.d("Vungle rewarded video ad successfully loaded.");
        mIsLoading = false;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                MoPubRewardedVideoManager.onRewardedVideoLoadSuccess(VungleRewardedVideo.class,
                        VUNGLE_AD_NETWORK_CONSTANT);
            }
        });
    }

    @Override
    protected void onInvalidate() {
        mIsLoading = false;
    }

    private class VungleRewardedVideoListener implements EventListener,
            CustomEventRewardedVideoListener {

        @Override
        public void onAdEnd(final boolean wasCallToActionClicked) {
            if (wasCallToActionClicked) {
                MoPubRewardedVideoManager.onRewardedVideoClicked(VungleRewardedVideo.class,
                        VUNGLE_AD_NETWORK_CONSTANT);
            }
            MoPubRewardedVideoManager.onRewardedVideoClosed(VungleRewardedVideo.class,
                    VUNGLE_AD_NETWORK_CONSTANT);
        }

        @Override
        public void onAdStart() {
            MoPubRewardedVideoManager.onRewardedVideoStarted(VungleRewardedVideo.class,
                    VUNGLE_AD_NETWORK_CONSTANT);
        }

        @Override
        public void onAdUnavailable(final String s) {
            MoPubRewardedVideoManager.onRewardedVideoLoadFailure(VungleRewardedVideo.class,
                    VUNGLE_AD_NETWORK_CONSTANT, MoPubErrorCode.NETWORK_NO_FILL);
        }

        @Override
        public void onAdPlayableChanged(final boolean playable) {
            if (mIsLoading && playable) {
                notifyAdAvailable();
            }
        }

        @Override
        public void onVideoView(final boolean isCompletedView, final int watchedMillis, final int videoMillis) {
            MoPubLog.d(String.format(Locale.US, "%.1f%% of Vungle video watched.",
                    (double) watchedMillis / videoMillis * 100));
            if (isCompletedView) {
                // Vungle does not provide a callback when a user should be rewarded.
                // You will need to provide your own reward logic if you receive a reward with
                // "NO_REWARD_LABEL" && "NO_REWARD_AMOUNT"
                MoPubRewardedVideoManager.onRewardedVideoCompleted(VungleRewardedVideo.class,
                        VUNGLE_AD_NETWORK_CONSTANT,
                        MoPubReward.success(MoPubReward.NO_REWARD_LABEL, MoPubReward.NO_REWARD_AMOUNT));
            }
        }
    }

    public static class VungleMediationSettings implements MediationSettings {
        @Nullable private final String userId;
        @Nullable private final String title;
        @Nullable private final String body;
        @Nullable private final String closeButtonText;
        @Nullable private final String keepWatchingButtonText;

        public static class Builder {
            @Nullable private String userId;
            @Nullable private String title;
            @Nullable private String body;
            @Nullable private String closeButtonText;
            @Nullable private String keepWatchingButtonText;

            public Builder withUserId(@NonNull final String userId) {
                this.userId = userId;
                return this;
            }

            public Builder withCancelDialogTitle(@NonNull final String title) {
                this.title = title;
                return this;
            }

            public Builder withCancelDialogBody(@NonNull final String body) {
                this.body = body;
                return this;
            }

            public Builder withCancelDialogCloseButton(@NonNull final String buttonText) {
                this.closeButtonText = buttonText;
                return this;
            }

            public Builder withCancelDialogKeepWatchingButton(@NonNull final String buttonText) {
                this.keepWatchingButtonText = buttonText;
                return this;
            }

            public VungleMediationSettings build() {
                return new VungleMediationSettings(this);
            }
        }

        private VungleMediationSettings(@NonNull final Builder builder) {
            this.userId = builder.userId;
            this.title = builder.title;
            this.body = builder.body;
            this.closeButtonText = builder.closeButtonText;
            this.keepWatchingButtonText = builder.keepWatchingButtonText;
        }
    }
}
