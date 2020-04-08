// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.simpleadsdemo;

import com.skillz.mopub.mobileads.MoPubView;

public class MediumRectangleDetailFragment extends AbstractBannerDetailFragment {

    @Override
    public MoPubView.MoPubAdSize getAdSize() {
        return MoPubView.MoPubAdSize.HEIGHT_280;
    }
}