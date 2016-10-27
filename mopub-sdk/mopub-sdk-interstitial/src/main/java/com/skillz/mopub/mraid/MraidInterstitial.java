package com.skillz.mopub.mraid;


import android.support.annotation.NonNull;

import com.skillz.mopub.mobileads.MraidActivity;
import com.skillz.mopub.mobileads.ResponseBodyInterstitial;

import java.util.Map;

import static com.skillz.mopub.common.DataKeys.HTML_RESPONSE_BODY_KEY;

class MraidInterstitial extends ResponseBodyInterstitial {
    private String mHtmlData;

    @Override
    protected void extractExtras(Map<String, String> serverExtras) {
        mHtmlData = serverExtras.get(HTML_RESPONSE_BODY_KEY);
    }

    @Override
    protected void preRenderHtml(@NonNull CustomEventInterstitialListener
            customEventInterstitialListener) {
        MraidActivity.preRenderHtml(mContext, customEventInterstitialListener, mHtmlData);
    }

    @Override
    public void showInterstitial() {
        MraidActivity.start(mContext, mAdReport, mHtmlData, mBroadcastIdentifier);
    }
}
