package com.skillz.mopub.nativeads;

import android.support.annotation.NonNull;

import com.skillz.mopub.nativeads.MoPubNativeAdPositioning.MoPubClientPositioning;

/**
 * Allows asynchronously requesting positioning information.
 */
interface PositioningSource {

    interface PositioningListener {
        void onLoad(@NonNull MoPubClientPositioning positioning);

        void onFailed();
    }

    void loadPositions(@NonNull String adUnitId, @NonNull PositioningListener listener);

}
