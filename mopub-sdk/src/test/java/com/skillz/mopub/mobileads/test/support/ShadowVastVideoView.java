package com.skillz.mopub.mobileads.test.support;

import com.skillz.mopub.mobileads.VastVideoView;

import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowVideoView;

@Implements(VastVideoView.class)
public class ShadowVastVideoView extends ShadowVideoView {
}
