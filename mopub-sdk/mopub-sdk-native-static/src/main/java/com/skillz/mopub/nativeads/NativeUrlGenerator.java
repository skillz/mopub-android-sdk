// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.nativeads;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.skillz.mopub.common.AdUrlGenerator;
import com.skillz.mopub.common.ClientMetadata;
import com.skillz.mopub.common.Constants;
import com.skillz.mopub.common.MoPub;

class NativeUrlGenerator extends AdUrlGenerator {
    @Nullable private String mDesiredAssets;
    @Nullable private String mSequenceNumber;

    NativeUrlGenerator(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public NativeUrlGenerator withAdUnitId(final String adUnitId) {
        mAdUnitId = adUnitId;
        return this;
    }

    @NonNull
    NativeUrlGenerator withRequest(@Nullable final RequestParameters requestParameters) {
        if (requestParameters != null) {
            final boolean canCollectPersonalInformation = MoPub.canCollectPersonalInformation();

            mUserDataKeywords = canCollectPersonalInformation ? requestParameters.getUserDataKeywords() : null;

            mKeywords = requestParameters.getKeywords();
            mDesiredAssets = requestParameters.getDesiredAssets();
        }
        return this;
    }

    @NonNull
    NativeUrlGenerator withSequenceNumber(final int sequenceNumber) {
        mSequenceNumber = String.valueOf(sequenceNumber);
        return this;
    }

    @Override
    public String generateUrlString(final String serverHostname) {
        initUrlString(serverHostname, Constants.AD_HANDLER);

        ClientMetadata clientMetadata = ClientMetadata.getInstance(mContext);
        addBaseParams(clientMetadata);

        setDesiredAssets();

        setSequenceNumber();

        return getFinalUrlString();
    }

    private void setSequenceNumber() {
       if (!TextUtils.isEmpty(mSequenceNumber)) {
           addParam("MAGIC_NO", mSequenceNumber);
       }
    }

    private void setDesiredAssets() {
        if (!TextUtils.isEmpty(mDesiredAssets)) {
            addParam("assets", mDesiredAssets);
        }
    }
}
