package com.skillz.mopub.mraid;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.skillz.mopub.mobileads.MraidActivity;
import com.skillz.mopub.mobileads.ResponseBodyInterstitial;

import java.util.Map;

import static com.skillz.mopub.common.DataKeys.HTML_RESPONSE_BODY_KEY;

class MraidInterstitial extends ResponseBodyInterstitial {
    @Nullable protected String mHtmlData;

    @Override
    protected void extractExtras(Map<String, String> serverExtras) {
        mHtmlData = serverExtras.get(HTML_RESPONSE_BODY_KEY);
    }

    @Override
    protected void preRenderHtml(@NonNull CustomEventInterstitialListener
            customEventInterstitialListener) {
        MraidActivity.preRenderHtml(this, mContext, customEventInterstitialListener, mHtmlData,
                mBroadcastIdentifier);
    }

    @Override
    public void showInterstitial() {
        MraidActivity.start(mContext, mAdReport, mHtmlData, mBroadcastIdentifier);
    }
}
