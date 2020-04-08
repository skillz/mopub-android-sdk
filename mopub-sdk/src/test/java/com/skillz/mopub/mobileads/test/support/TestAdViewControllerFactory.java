// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import android.content.Context;

import com.skillz.mopub.mobileads.AdViewController;
import com.skillz.mopub.mobileads.MoPubView;
import com.skillz.mopub.mobileads.factories.AdViewControllerFactory;

import static org.mockito.Mockito.mock;

public class TestAdViewControllerFactory extends AdViewControllerFactory {
    private AdViewController mockAdViewController = mock(AdViewController.class);

    public static AdViewController getSingletonMock() {
        return getTestFactory().mockAdViewController;
    }

    private static TestAdViewControllerFactory getTestFactory() {
        return ((TestAdViewControllerFactory) instance);
    }

    @Override
    protected AdViewController internalCreate(Context context, MoPubView moPubView) {
        return mockAdViewController;
    }
}
