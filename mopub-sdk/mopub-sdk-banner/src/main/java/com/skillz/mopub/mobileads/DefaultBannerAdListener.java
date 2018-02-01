package com.skillz.mopub.mobileads;

import com.skillz.mopub.mobileads.MoPubErrorCode;
import com.skillz.mopub.mobileads.MoPubView;

import static com.skillz.mopub.mobileads.MoPubView.BannerAdListener;

public class DefaultBannerAdListener implements BannerAdListener {
    @Override public void onBannerLoaded(MoPubView banner) { }
    @Override public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) { }
    @Override public void onBannerClicked(MoPubView banner) { }
    @Override public void onBannerExpanded(MoPubView banner) { }
    @Override public void onBannerCollapsed(MoPubView banner) { }
}
