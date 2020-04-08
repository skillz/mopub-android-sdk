// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.factories;

import android.content.Context;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.HtmlInterstitialWebView;

import static com.skillz.mopub.mobileads.CustomEventInterstitial.CustomEventInterstitialListener;

public class HtmlInterstitialWebViewFactory {
    protected static HtmlInterstitialWebViewFactory instance = new HtmlInterstitialWebViewFactory();

    public static HtmlInterstitialWebView create(
            Context context,
            AdReport adReport,
            CustomEventInterstitialListener customEventInterstitialListener,
            String clickthroughUrl) {
        return instance.internalCreate(context, adReport, customEventInterstitialListener, clickthroughUrl);
    }

    public HtmlInterstitialWebView internalCreate(
            Context context,
            AdReport adReport,
            CustomEventInterstitialListener customEventInterstitialListener,
            String clickthroughUrl) {
        HtmlInterstitialWebView htmlInterstitialWebView = new HtmlInterstitialWebView(context, adReport);
        htmlInterstitialWebView.init(customEventInterstitialListener, clickthroughUrl, adReport.getDspCreativeId());
        return htmlInterstitialWebView;
    }

    @Deprecated // for testing
    public static void setInstance(HtmlInterstitialWebViewFactory factory) {
        instance = factory;
    }
}
