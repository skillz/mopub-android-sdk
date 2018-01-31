package com.skillz.mopub.common;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skillz.mopub.common.logging.MoPubLog;
import com.skillz.mopub.common.util.Drawables;

import java.util.EnumSet;

class BrowserWebViewClient extends WebViewClient {

    private static final EnumSet<UrlAction> SUPPORTED_URL_ACTIONS = EnumSet.of(
            UrlAction.HANDLE_PHONE_SCHEME,
            UrlAction.OPEN_APP_MARKET,
            UrlAction.OPEN_IN_APP_BROWSER,
            UrlAction.HANDLE_SHARE_TWEET,
            UrlAction.FOLLOW_DEEP_LINK_WITH_FALLBACK,
            UrlAction.FOLLOW_DEEP_LINK
    );

    @NonNull
    private MoPubBrowser mMoPubBrowser;

    public BrowserWebViewClient(@NonNull final MoPubBrowser moPubBrowser) {
        mMoPubBrowser = moPubBrowser;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description,
            String failingUrl) {
        MoPubLog.d("MoPubBrowser error: " + description);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        UrlHandler urlHandler = new UrlHandler.Builder()
                .withSupportedUrlActions(SUPPORTED_URL_ACTIONS)
                .withoutMoPubBrowser()
                .withResultActions(new UrlHandler.ResultActions() {
                    @Override
                    public void urlHandlingSucceeded(@NonNull String url,
                            @NonNull UrlAction urlAction) {
                        if (urlAction.equals(UrlAction.OPEN_IN_APP_BROWSER)) {
                            mMoPubBrowser.getWebView().loadUrl(url);
                        } else {
                            // UrlAction opened in external app, so close MoPubBrowser
                            mMoPubBrowser.finish();
                        }
                    }

                    @Override
                    public void urlHandlingFailed(@NonNull String url,
                            @NonNull UrlAction lastFailedUrlAction) {
                    }
                })
                .build();

        return urlHandler.handleResolvedUrl(mMoPubBrowser.getApplicationContext(), url,
                true, // = fromUserInteraction
                null // = trackingUrls
        );
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        Drawable backImageDrawable = view.canGoBack()
                ? Drawables.LEFT_ARROW.createDrawable(mMoPubBrowser)
                : Drawables.UNLEFT_ARROW.createDrawable(mMoPubBrowser);
        mMoPubBrowser.getBackButton().setImageDrawable(backImageDrawable);

        Drawable forwardImageDrawable = view.canGoForward()
                ? Drawables.RIGHT_ARROW.createDrawable(mMoPubBrowser)
                : Drawables.UNRIGHT_ARROW.createDrawable(mMoPubBrowser);
        mMoPubBrowser.getForwardButton().setImageDrawable(forwardImageDrawable);
    }
}
