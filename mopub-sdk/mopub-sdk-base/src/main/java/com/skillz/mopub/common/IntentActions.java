// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.common;

/**
 * IntentActions are used by a {@link com.skillz.mopub.mobileads.BaseBroadcastReceiver}
 * to relay information about the current state of a custom event activity.
 */
public class IntentActions {
    public static final String ACTION_INTERSTITIAL_FAIL = "com.skillz.mopub.action.interstitial.fail";
    public static final String ACTION_INTERSTITIAL_SHOW = "com.skillz.mopub.action.interstitial.show";
    public static final String ACTION_INTERSTITIAL_DISMISS = "com.skillz.mopub.action.interstitial.dismiss";
    public static final String ACTION_INTERSTITIAL_CLICK = "com.skillz.mopub.action.interstitial.click";

    public static final String ACTION_REWARDED_VIDEO_COMPLETE = "com.skillz.mopub.action.rewardedvideo.complete";
    public static final String ACTION_REWARDED_PLAYABLE_COMPLETE = "com.skillz.mopub.action.rewardedplayable.complete";
    private IntentActions() {}
}
