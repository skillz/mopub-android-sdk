// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads;

import androidx.annotation.NonNull;

import com.skillz.mopub.common.CreativeOrientation;
import com.skillz.mopub.common.logging.MoPubLog;

import java.util.Map;

import static com.skillz.mopub.common.DataKeys.CLICKTHROUGH_URL_KEY;
import static com.skillz.mopub.common.DataKeys.CREATIVE_ORIENTATION_KEY;
import static com.skillz.mopub.common.DataKeys.HTML_RESPONSE_BODY_KEY;
import static com.skillz.mopub.common.logging.MoPubLog.AdapterLogEvent.LOAD_ATTEMPTED;
import static com.skillz.mopub.common.logging.MoPubLog.AdapterLogEvent.SHOW_ATTEMPTED;

public class HtmlInterstitial extends ResponseBodyInterstitial {
    public static final String ADAPTER_NAME = HtmlInterstitial.class.getSimpleName();
    private String mHtmlData;
    private String mClickthroughUrl;
    @NonNull
    private CreativeOrientation mOrientation;

    @Override
    protected void extractExtras(Map<String, String> serverExtras) {
        mHtmlData = serverExtras.get(HTML_RESPONSE_BODY_KEY);
        mClickthroughUrl = serverExtras.get(CLICKTHROUGH_URL_KEY);
        mOrientation = CreativeOrientation.fromString(serverExtras.get(CREATIVE_ORIENTATION_KEY));
    }

    @Override
    protected void preRenderHtml(CustomEventInterstitialListener customEventInterstitialListener) {
        MoPubLog.log(LOAD_ATTEMPTED, ADAPTER_NAME);
        MoPubActivity.preRenderHtml(this, mContext, mAdReport, customEventInterstitialListener, mClickthroughUrl, mBroadcastIdentifier);
    }

    @Override
    public void showInterstitial() {
        MoPubLog.log(SHOW_ATTEMPTED, ADAPTER_NAME);
        MoPubActivity.start(mContext, mAdReport, mClickthroughUrl, mOrientation,
                mBroadcastIdentifier);
    }
}
