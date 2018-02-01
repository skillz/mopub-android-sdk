package com.skillz.mopub.mobileads;

import android.app.Activity;
import android.content.Intent;

import com.skillz.mopub.common.test.support.SdkTestRunner;
import com.skillz.mopub.mobileads.BuildConfig;
import com.skillz.mopub.mobileads.test.support.TestHtmlInterstitialWebViewFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.support.v4.ShadowLocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

import static com.skillz.mopub.common.DataKeys.BROADCAST_IDENTIFIER_KEY;
import static com.skillz.mopub.common.DataKeys.CLICKTHROUGH_URL_KEY;
import static com.skillz.mopub.common.DataKeys.HTML_RESPONSE_BODY_KEY;
import static com.skillz.mopub.common.DataKeys.REDIRECT_URL_KEY;
import static com.skillz.mopub.common.DataKeys.SCROLLABLE_KEY;
import static com.skillz.mopub.mobileads.CustomEventInterstitial.CustomEventInterstitialListener;
import static com.skillz.mopub.common.IntentActions.ACTION_INTERSTITIAL_DISMISS;
import static com.skillz.mopub.common.IntentActions.ACTION_INTERSTITIAL_SHOW;
import static com.skillz.mopub.mobileads.MoPubErrorCode.NETWORK_INVALID_STATE;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SdkTestRunner.class)
@Config(constants = BuildConfig.class)
public class HtmlInterstitialTest extends ResponseBodyInterstitialTest {
    private CustomEventInterstitialListener customEventInterstitialListener;
    private Activity context;
    private Map<String,Object> localExtras;
    private Map<String,String> serverExtras;
    private HtmlInterstitialWebView htmlInterstitialWebView;
    private String expectedResponse;
    private long broadcastIdentifier;

    @Before
    public void setUp() throws Exception {
        subject = new HtmlInterstitial();

        expectedResponse = "this is the response";
        htmlInterstitialWebView = TestHtmlInterstitialWebViewFactory.getSingletonMock();
        context = new Activity();
        customEventInterstitialListener = mock(CustomEventInterstitialListener.class);
        localExtras = new HashMap<String, Object>();
        serverExtras = new HashMap<String, String>();
        serverExtras.put(HTML_RESPONSE_BODY_KEY, expectedResponse);

        broadcastIdentifier = 2222;
        localExtras.put(BROADCAST_IDENTIFIER_KEY, broadcastIdentifier);
    }

    @Ignore("pending")
    @Test
    public void loadInterstitial_shouldNotifyCustomEventInterstitialListenerOnLoaded() throws Exception {
        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);

//        verify(customEventInterstitialListener).onInterstitialLoaded();
    }

    @Test
    public void loadInterstitial_whenNoHtmlResponsePassedIn_shouldCallLoadFailUrl() throws Exception {
        serverExtras.remove(HTML_RESPONSE_BODY_KEY);
        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);

        assertThat(TestHtmlInterstitialWebViewFactory.getLatestListener()).isNull();
        assertThat(TestHtmlInterstitialWebViewFactory.getLatestIsScrollable()).isFalse();
        assertThat(TestHtmlInterstitialWebViewFactory.getLatestRedirectUrl()).isNull();
        assertThat(TestHtmlInterstitialWebViewFactory.getLatestClickthroughUrl()).isNull();
        verify(customEventInterstitialListener).onInterstitialFailed(NETWORK_INVALID_STATE);
        verify(htmlInterstitialWebView, never()).loadHtmlResponse(anyString());
    }


    @Test
    public void showInterstitial_withMinimumExtras_shouldStartMoPubActivityWithDefaults() throws Exception {
        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);
        subject.showInterstitial();

        Intent nextStartedActivity = ShadowApplication.getInstance().getNextStartedActivity();
        assertThat(nextStartedActivity.getStringExtra(HTML_RESPONSE_BODY_KEY)).isEqualTo(expectedResponse);
        assertThat(nextStartedActivity.getBooleanExtra(SCROLLABLE_KEY, false)).isFalse();
        assertThat(nextStartedActivity.getStringExtra(REDIRECT_URL_KEY)).isNull();
        assertThat(nextStartedActivity.getStringExtra(CLICKTHROUGH_URL_KEY)).isNull();
        assertThat(nextStartedActivity.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isNotEqualTo(0);
        assertThat(nextStartedActivity.getComponent().getClassName()).isEqualTo("MoPubActivity");
    }

    @Test
    public void showInterstitial_shouldStartMoPubActivityWithAllExtras() throws Exception {
        serverExtras.put(SCROLLABLE_KEY, "true");
        serverExtras.put(REDIRECT_URL_KEY, "redirectUrl");
        serverExtras.put(CLICKTHROUGH_URL_KEY, "clickthroughUrl");

        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);
        subject.showInterstitial();

        Intent nextStartedActivity = ShadowApplication.getInstance().getNextStartedActivity();
        assertThat(nextStartedActivity.getStringExtra(HTML_RESPONSE_BODY_KEY)).isEqualTo(expectedResponse);
        assertThat(nextStartedActivity.getBooleanExtra(SCROLLABLE_KEY, false)).isTrue();
        assertThat(nextStartedActivity.getStringExtra(REDIRECT_URL_KEY)).isEqualTo("redirectUrl");
        assertThat(nextStartedActivity.getStringExtra(CLICKTHROUGH_URL_KEY)).isEqualTo("clickthroughUrl");
        assertThat(nextStartedActivity.getFlags() & Intent.FLAG_ACTIVITY_NEW_TASK).isNotEqualTo(0);
        assertThat(nextStartedActivity.getComponent().getClassName()).isEqualTo("MoPubActivity");
    }

    @Test
    public void loadInterstitial_shouldConnectListenerToBroadcastReceiver() throws Exception {
        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);

        Intent intent = EventForwardingBroadcastReceiverTest.getIntentForActionAndIdentifier(ACTION_INTERSTITIAL_SHOW, broadcastIdentifier);
        ShadowLocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        verify(customEventInterstitialListener).onInterstitialShown();

        intent = EventForwardingBroadcastReceiverTest.getIntentForActionAndIdentifier(ACTION_INTERSTITIAL_DISMISS, broadcastIdentifier);
        ShadowLocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        verify(customEventInterstitialListener).onInterstitialDismissed();
    }

    @Test
    public void onInvalidate_shouldDisconnectListenerToBroadcastReceiver() throws Exception {
        subject.loadInterstitial(context, customEventInterstitialListener, localExtras, serverExtras);
        subject.onInvalidate();

        Intent intent;
        intent = new Intent(ACTION_INTERSTITIAL_SHOW);
        ShadowLocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        verify(customEventInterstitialListener, never()).onInterstitialShown();

        intent = new Intent(ACTION_INTERSTITIAL_DISMISS);
        ShadowLocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        verify(customEventInterstitialListener, never()).onInterstitialDismissed();
    }
}
