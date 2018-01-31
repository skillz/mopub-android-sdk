package com.skillz.mopub.mobileads.factories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.skillz.mopub.common.AdReport;
import com.skillz.mopub.common.VisibleForTesting;
import com.skillz.mopub.mraid.MraidController;
import com.skillz.mopub.mraid.PlacementType;

public class MraidControllerFactory {
    protected static MraidControllerFactory instance = new MraidControllerFactory();

    @VisibleForTesting
    public static void setInstance(MraidControllerFactory factory) {
        instance = factory;
    }

    public static MraidController create(@NonNull final Context context,
                                         @NonNull final AdReport adReport,
                                         @NonNull final PlacementType placementType) {
        return instance.internalCreate(context, adReport, placementType);
    }

    protected MraidController internalCreate(@NonNull final Context context, 
            @NonNull final AdReport adReport, 
            @NonNull final PlacementType placementType) {
        return new MraidController(context, adReport, placementType);
    }
}
