package com.skillz.mopub.simpleadsdemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mopub.simpleadsdemo.R;

/**
 * ViewHolder data object that parses and stores named child Views for sample app DetailFragments,
 * e.g. {@link InterstitialDetailFragment}.
 */
class DetailFragmentViewHolder {
    @NonNull final TextView mDescriptionView;
    @NonNull final Button mLoadButton;
    @Nullable final Button mShowButton;
    @NonNull final TextView mAdUnitIdView;
    @NonNull final EditText mKeywordsField;
    @Nullable final EditText mCustomDataField;

    /**
     * Internal constructor. Use {@link #fromView(View)} to create instances of this class.
     *
     * @param descriptionView Displays ad full name, e.g. "MoPub Banner Sample"
     * @param adUnitIdView Displays adUnitId
     * @param loadButton Loads an ad. For non-cached ad formats, this will also display the ad
     * @param showButton Displays an ad. (optional, only defined for interstitial and rewarded ads)
     * @param keywordsField Keyword entry field. This is eventually passed in the 'q' query
     *                      parameter in the ad request
     * @param customDataField Custom data entry field. Used to include arbitrary data to rewarded
     *                        completion URLs. View visibility defaults to {@link View#GONE}.
     *                        (optional, only defined for rewarded ads)
     */
    private DetailFragmentViewHolder(
            @NonNull final TextView descriptionView,
            @NonNull final TextView adUnitIdView,
            @NonNull final Button loadButton,
            @Nullable final Button showButton,
            @NonNull final EditText keywordsField,
            @Nullable final EditText customDataField) {
        mDescriptionView = descriptionView;
        mAdUnitIdView = adUnitIdView;
        mLoadButton = loadButton;
        mShowButton = showButton;
        mKeywordsField = keywordsField;
        mCustomDataField = customDataField;
    }

    static DetailFragmentViewHolder fromView(@NonNull final View view) {
        final TextView descriptionView = (TextView) view.findViewById(R.id.description);
        final TextView adUnitIdView = (TextView) view.findViewById(R.id.ad_unit_id);
        final Button loadButton = (Button) view.findViewById(R.id.load_button);
        final Button showButton = (Button) view.findViewById(R.id.show_button);
        final EditText keywordsField = (EditText) view.findViewById(R.id.keywords_field);
        final EditText customDataField = (EditText) view.findViewById(R.id.custom_data_field);

        return new DetailFragmentViewHolder(descriptionView, adUnitIdView, loadButton, showButton,
                keywordsField, customDataField);
    }
}