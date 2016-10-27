package com.skillz.mopub.mobileads.factories;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.CustomEventInterstitialAdapter;
import com.skillz.mopub.mobileads.MoPubInterstitial;

import java.util.Map;

public class CustomEventInterstitialAdapterFactory {
    protected static CustomEventInterstitialAdapterFactory instance = new CustomEventInterstitialAdapterFactory();

    @Deprecated // for testing
    public static void setInstance(CustomEventInterstitialAdapterFactory factory) {
        instance = factory;
    }

    public static CustomEventInterstitialAdapter create(MoPubInterstitial moPubInterstitial, String className, Map<String, String> serverExtras, long broadcastIdentifier, AdReport adReport) {
        return instance.internalCreate(moPubInterstitial, className, serverExtras, broadcastIdentifier, adReport);
    }

    protected CustomEventInterstitialAdapter internalCreate(MoPubInterstitial moPubInterstitial, String className, Map<String, String> serverExtras, long broadcastIdentifier, AdReport adReport) {
        return new CustomEventInterstitialAdapter(moPubInterstitial, className, serverExtras, broadcastIdentifier, adReport);
    }
}
