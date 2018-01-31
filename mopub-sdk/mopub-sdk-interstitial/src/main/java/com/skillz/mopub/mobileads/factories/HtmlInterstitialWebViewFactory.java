package com.skillz.mopub.mobileads.factories;

import android.content.Context;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.HtmlInterstitialWebView;
import com.skillz.mopub.mobileads.CustomEventInterstitial;

public class HtmlInterstitialWebViewFactory {
    protected static HtmlInterstitialWebViewFactory instance = new HtmlInterstitialWebViewFactory();

    public static HtmlInterstitialWebView create(
            Context context,
            AdReport adReport,
            CustomEventInterstitial.CustomEventInterstitialListener customEventInterstitialListener,
            boolean isScrollable,
            String redirectUrl,
            String clickthroughUrl) {
        return instance.internalCreate(context, adReport, customEventInterstitialListener, isScrollable, redirectUrl, clickthroughUrl);
    }

    public HtmlInterstitialWebView internalCreate(
            Context context,
            AdReport adReport,
            CustomEventInterstitial.CustomEventInterstitialListener customEventInterstitialListener,
            boolean isScrollable,
            String redirectUrl,
            String clickthroughUrl) {
        HtmlInterstitialWebView htmlInterstitialWebView = new HtmlInterstitialWebView(context, adReport);
        htmlInterstitialWebView.init(customEventInterstitialListener, isScrollable, redirectUrl, clickthroughUrl, adReport.getDspCreativeId());
        return htmlInterstitialWebView;
    }

    @Deprecated // for testing
    public static void setInstance(HtmlInterstitialWebViewFactory factory) {
        instance = factory;
    }
}
