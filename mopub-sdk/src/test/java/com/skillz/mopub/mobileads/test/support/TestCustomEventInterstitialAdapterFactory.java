// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.CustomEventInterstitialAdapter;
import com.skillz.mopub.mobileads.MoPubInterstitial;
import com.skillz.mopub.mobileads.factories.CustomEventInterstitialAdapterFactory;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class TestCustomEventInterstitialAdapterFactory extends CustomEventInterstitialAdapterFactory{
    private CustomEventInterstitialAdapter mockCustomEventInterstitalAdapter = mock(CustomEventInterstitialAdapter.class);
    private MoPubInterstitial latestMoPubInterstitial;
    private String latestClassName;
    private Map<String, String> latestClassData;

    public static CustomEventInterstitialAdapter getSingletonMock() {
        return getTestFactory().mockCustomEventInterstitalAdapter;
    }

    private static TestCustomEventInterstitialAdapterFactory getTestFactory() {
        return ((TestCustomEventInterstitialAdapterFactory)instance);
    }

    public static MoPubInterstitial getLatestMoPubInterstitial() {
        return getTestFactory().latestMoPubInterstitial;
    }

    public static String getLatestClassName() {
        return getTestFactory().latestClassName;
    }

    public static Map<String, String> getLatestServerExtras() {
        return getTestFactory().latestClassData;
    }

    @Override
    protected CustomEventInterstitialAdapter internalCreate(MoPubInterstitial moPubInterstitial, String className, Map<String, String> serverExtras, long broadcastIdentifier, AdReport adReport) {
        latestMoPubInterstitial = moPubInterstitial;
        latestClassName = className;
        latestClassData = serverExtras;
        return mockCustomEventInterstitalAdapter;
    }
}
