package com.skillz.mopub.mobileads.factories;

import android.support.annotation.NonNull;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.CustomEventBannerAdapter;
import com.skillz.mopub.mobileads.MoPubView;

import java.util.Map;

public class CustomEventBannerAdapterFactory {
    protected static CustomEventBannerAdapterFactory instance = new CustomEventBannerAdapterFactory();

    @Deprecated // for testing
    public static void setInstance(CustomEventBannerAdapterFactory factory) {
        instance = factory;
    }

    public static CustomEventBannerAdapter create(@NonNull MoPubView moPubView,
            @NonNull String className,
            @NonNull Map<String, String> serverExtras,
            long broadcastIdentifier,
            @NonNull AdReport adReport) {
        return instance.internalCreate(moPubView, className, serverExtras, broadcastIdentifier, adReport);
    }

    protected CustomEventBannerAdapter internalCreate(@NonNull MoPubView moPubView,
            @NonNull String className,
            @NonNull Map<String, String> serverExtras,
            long broadcastIdentifier,
            @NonNull AdReport adReport) {
        return new CustomEventBannerAdapter(moPubView, className, serverExtras, broadcastIdentifier, adReport);
    }
}
