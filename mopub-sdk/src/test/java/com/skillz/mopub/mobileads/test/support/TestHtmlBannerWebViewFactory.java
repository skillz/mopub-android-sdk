// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import android.content.Context;
import android.webkit.WebSettings;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.HtmlBannerWebView;
import com.skillz.mopub.mobileads.factories.HtmlBannerWebViewFactory;

import static com.skillz.mopub.mobileads.CustomEventBanner.CustomEventBannerListener;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHtmlBannerWebViewFactory extends HtmlBannerWebViewFactory {
    private HtmlBannerWebView mockHtmlBannerWebView = mock(HtmlBannerWebView.class);
    private CustomEventBannerListener latestListener;
    private String latestClickthroughUrl;
    private AdReport latestAdReport;

    public TestHtmlBannerWebViewFactory() {
        WebSettings webSettings = mock(WebSettings.class);
        when(mockHtmlBannerWebView.getSettings()).thenReturn(webSettings);
        when(webSettings.getUserAgentString()).thenReturn("Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
    }

    public static HtmlBannerWebView getSingletonMock() {
        return getTestFactory().mockHtmlBannerWebView;
    }

    private static TestHtmlBannerWebViewFactory getTestFactory() {
        return (TestHtmlBannerWebViewFactory) instance;
    }

    @Override
    public HtmlBannerWebView internalCreate(
            Context context,
            AdReport adReport,
            CustomEventBannerListener customEventBannerListener,
            String clickthroughUrl) {
        latestListener = customEventBannerListener;
        latestClickthroughUrl = clickthroughUrl;
        latestAdReport = adReport;
        return mockHtmlBannerWebView;
    }

    public static CustomEventBannerListener getLatestListener() {
        return getTestFactory().latestListener;
    }

    public static String getLatestClickthroughUrl() {
        return getTestFactory().latestClickthroughUrl;
    }

    public static AdReport getLatestAdReport() {
        return getTestFactory().latestAdReport;
    }
}
