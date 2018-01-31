package com.skillz.mopub.mobileads;

import android.content.Context;

import com.skillz.mopub.common.AdUrlGenerator;
import com.skillz.mopub.common.ClientMetadata;
import com.skillz.mopub.common.Constants;

import static com.skillz.mopub.common.ExternalViewabilitySessionManager.ViewabilityVendor;

public class WebViewAdUrlGenerator extends AdUrlGenerator {
    private final boolean mIsStorePictureSupported;

    public WebViewAdUrlGenerator(Context context, boolean isStorePictureSupported) {
        super(context);
        mIsStorePictureSupported = isStorePictureSupported;
    }

    @Override
    public String generateUrlString(String serverHostname) {
        initUrlString(serverHostname, Constants.AD_HANDLER);

        setApiVersion("6");

        final ClientMetadata clientMetadata = ClientMetadata.getInstance(mContext);
        addBaseParams(clientMetadata);

        setMraidFlag(true);

        setExternalStoragePermission(mIsStorePictureSupported);

        enableViewability(ViewabilityVendor.getEnabledVendorKey());

        return getFinalUrlString();
    }
}
