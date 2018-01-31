package com.skillz.mopub.mobileads;

import android.content.Context;
import android.os.Handler;

import com.skillz.mopub.common.AdReport;

public class HtmlInterstitialWebView extends BaseHtmlWebView {
    private Handler mHandler;

    public HtmlInterstitialWebView(Context context, AdReport adReport) {
        super(context, adReport);

        mHandler = new Handler();
    }

    public void init(final CustomEventInterstitial.CustomEventInterstitialListener customEventInterstitialListener, boolean isScrollable, String redirectUrl, String clickthroughUrl, String dspCreativeId) {
        super.init(isScrollable);

        HtmlInterstitialWebViewListener htmlInterstitialWebViewListener = new HtmlInterstitialWebViewListener(customEventInterstitialListener);
        HtmlWebViewClient htmlWebViewClient = new HtmlWebViewClient(htmlInterstitialWebViewListener, this, clickthroughUrl, redirectUrl, dspCreativeId);
        setWebViewClient(htmlWebViewClient);
    }

    private void postHandlerRunnable(Runnable r) {
        mHandler.post(r);
    }

    static class HtmlInterstitialWebViewListener implements HtmlWebViewListener {
        private final CustomEventInterstitial.CustomEventInterstitialListener mCustomEventInterstitialListener;

        public HtmlInterstitialWebViewListener(CustomEventInterstitial.CustomEventInterstitialListener customEventInterstitialListener) {
            mCustomEventInterstitialListener = customEventInterstitialListener;
        }

        @Override
        public void onLoaded(BaseHtmlWebView mHtmlWebView) {
            mCustomEventInterstitialListener.onInterstitialLoaded();
        }

        @Override
        public void onFailed(MoPubErrorCode errorCode) {
            mCustomEventInterstitialListener.onInterstitialFailed(errorCode);
        }

        @Override
        public void onClicked() {
            mCustomEventInterstitialListener.onInterstitialClicked();
        }

        @Override
        public void onCollapsed() {
            // Ignored
        }
    }
}
