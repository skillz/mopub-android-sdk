// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads;

import android.content.Context;

import com.skillz.mopub.common.AdUrlGenerator;
import com.skillz.mopub.common.ClientMetadata;
import com.skillz.mopub.common.Constants;

import static com.skillz.mopub.common.ExternalViewabilitySessionManager.ViewabilityVendor;

public class WebViewAdUrlGenerator extends AdUrlGenerator {

    public WebViewAdUrlGenerator(Context context) {
        super(context);
    }

    @Override
    public String generateUrlString(String serverHostname) {
        initUrlString(serverHostname, Constants.AD_HANDLER);

        setApiVersion("6");

        final ClientMetadata clientMetadata = ClientMetadata.getInstance(mContext);
        addBaseParams(clientMetadata);

        setMraidFlag(true);

        enableViewability(ViewabilityVendor.getEnabledVendorKey());

        return getFinalUrlString();
    }
}
