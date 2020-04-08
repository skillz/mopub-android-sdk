// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.common;

import androidx.annotation.NonNull;

import com.skillz.mopub.mobileads.MoPubErrorCode;

public interface OnNetworkInitializationFinishedListener {
    void onNetworkInitializationFinished(@NonNull final Class<? extends AdapterConfiguration> clazz,
            @NonNull final MoPubErrorCode moPubErrorCode);
}
