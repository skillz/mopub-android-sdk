package com.skillz.mopub.mobileads.test.support;

import com.skillz.mopub.mobileads.CustomEventInterstitial;
import com.skillz.mopub.mobileads.factories.CustomEventInterstitialFactory;

import static org.mockito.Mockito.mock;

public class TestCustomEventInterstitialFactory extends CustomEventInterstitialFactory {
    private CustomEventInterstitial instance = mock(CustomEventInterstitial.class);

    @Override
    protected CustomEventInterstitial internalCreate(String className) {
        return instance;
    }
}
