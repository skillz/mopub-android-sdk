// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import com.skillz.mopub.mobileads.CustomEventBanner;
import com.skillz.mopub.mobileads.factories.CustomEventBannerFactory;

import static org.mockito.Mockito.mock;

public class TestCustomEventBannerFactory extends CustomEventBannerFactory{
    private CustomEventBanner instance = mock(CustomEventBanner.class);

    @Override
    protected CustomEventBanner internalCreate(String className) {
        return instance;
    }
}
