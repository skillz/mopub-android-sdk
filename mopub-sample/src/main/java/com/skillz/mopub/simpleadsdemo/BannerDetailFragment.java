package com.skillz.mopub.simpleadsdemo;

import com.mopub.simpleadsdemo.R;

public class BannerDetailFragment extends AbstractBannerDetailFragment {

    @Override
    public int getWidth() {
        return (int) getResources().getDimension(R.dimen.banner_width);
    }

    @Override
    public int getHeight() {
        return (int) getResources().getDimension(R.dimen.banner_height);
    }
}
