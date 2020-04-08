// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads.test.support;

import android.content.Context;
import androidx.annotation.NonNull;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.mobileads.factories.MraidControllerFactory;
import com.skillz.mopub.mraid.MraidController;
import com.skillz.mopub.mraid.PlacementType;

import static org.mockito.Mockito.mock;

public class TestMraidControllerFactory extends MraidControllerFactory {
    private MraidController mockMraidController = mock(MraidController.class);

    public static MraidController getSingletonMock() {
        return getTestFactory().mockMraidController;
    }

    private static TestMraidControllerFactory getTestFactory() {
        return ((TestMraidControllerFactory) MraidControllerFactory.instance);
    }

    @Override
    protected MraidController internalCreate(@NonNull final Context context,
            @NonNull AdReport adReport,
            @NonNull final PlacementType placementType) {
        return mockMraidController;
    }
}
