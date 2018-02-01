package com.skillz.mopub.simpleadsdemo;

import com.mopub.simpleadsdemo.R;

public class SkyscraperDetailFragment extends AbstractBannerDetailFragment {

    @Override
    public int getWidth() {
        return (int) getResources().getDimension(R.dimen.skyscraper_width);
    }

    @Override
    public int getHeight() {
        return (int) getResources().getDimension(R.dimen.skyscraper_height);
    }
}
