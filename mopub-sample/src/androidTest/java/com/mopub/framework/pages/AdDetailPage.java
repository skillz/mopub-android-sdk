// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.framework.pages;

import com.skillz.mopub.framework.base.BasePage;
import com.skillz.mopub.simpleadsdemo.R;

public class AdDetailPage extends BasePage {
    public void clickLoadAdButton() {
        clickElementWithId(R.id.load_button);
    }
}
