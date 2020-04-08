// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import androidx.annotation.NonNull;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.CustomEventBannerAdapter;
import com.skillz.mopub.mobileads.MoPubView;
import com.skillz.mopub.mobileads.factories.CustomEventBannerAdapterFactory;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class TestCustomEventBannerAdapterFactory extends CustomEventBannerAdapterFactory {
    private CustomEventBannerAdapter mockCustomEventBannerAdapter = mock(CustomEventBannerAdapter.class);
    private MoPubView moPubView;
    private String className;
    private Map<String, String> classData;

    public static CustomEventBannerAdapter getSingletonMock() {
        return getTestFactory().mockCustomEventBannerAdapter;
    }

    private static TestCustomEventBannerAdapterFactory getTestFactory() {
        return ((TestCustomEventBannerAdapterFactory) instance);
    }

    @Override
    protected CustomEventBannerAdapter internalCreate(@NonNull final MoPubView moPubView,
            @NonNull final String className,
            @NonNull final Map<String, String> serverExtras,
            final long broadcastIdentifier,
            @NonNull final AdReport adReport) {
        this.moPubView = moPubView;
        this.className = className;
        this.classData = serverExtras;
        return mockCustomEventBannerAdapter;
    }

    public static MoPubView getLatestMoPubView() {
        return getTestFactory().moPubView;
    }

    public static String getLatestClassName() {
        return getTestFactory().className;
    }

    public static Map<String, String> getLatestClassData() {
        return getTestFactory().classData;
    }
}
