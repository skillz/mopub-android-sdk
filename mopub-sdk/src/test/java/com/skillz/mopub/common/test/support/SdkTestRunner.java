// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.common.test.support;

import androidx.annotation.NonNull;

import com.skillz.mopub.common.CacheService;
import com.skillz.mopub.common.ClientMetadata;
import com.skillz.mopub.common.MoPub;
import com.skillz.mopub.common.Preconditions;
import com.skillz.mopub.common.factories.MethodBuilderFactory;
import com.skillz.mopub.common.util.AsyncTasks;
import com.skillz.mopub.common.util.DateAndTime;
import com.skillz.mopub.common.util.test.support.ShadowAsyncTasks;
import com.skillz.mopub.common.util.test.support.ShadowAvidAdSessionManager;
import com.skillz.mopub.common.util.test.support.ShadowAvidManager;
import com.skillz.mopub.common.util.test.support.ShadowMoPubHttpUrlConnection;
import com.skillz.mopub.common.util.test.support.ShadowMoatFactory;
import com.skillz.mopub.common.util.test.support.ShadowReflection;
import com.skillz.mopub.common.util.test.support.TestDateAndTime;
import com.skillz.mopub.common.util.test.support.TestMethodBuilderFactory;
import com.skillz.mopub.mobileads.factories.AdViewControllerFactory;
import com.skillz.mopub.mobileads.factories.CustomEventBannerAdapterFactory;
import com.skillz.mopub.mobileads.factories.CustomEventBannerFactory;
import com.skillz.mopub.mobileads.factories.CustomEventInterstitialAdapterFactory;
import com.skillz.mopub.mobileads.factories.CustomEventInterstitialFactory;
import com.skillz.mopub.mobileads.factories.HtmlBannerWebViewFactory;
import com.skillz.mopub.mobileads.factories.HtmlInterstitialWebViewFactory;
import com.skillz.mopub.mobileads.factories.MoPubViewFactory;
import com.skillz.mopub.mobileads.factories.MraidControllerFactory;
import com.skillz.mopub.mobileads.factories.VastManagerFactory;
import com.skillz.mopub.mobileads.test.support.TestAdViewControllerFactory;
import com.skillz.mopub.mobileads.test.support.TestCustomEventBannerAdapterFactory;
import com.skillz.mopub.mobileads.test.support.TestCustomEventBannerFactory;
import com.skillz.mopub.mobileads.test.support.TestCustomEventInterstitialAdapterFactory;
import com.skillz.mopub.mobileads.test.support.TestCustomEventInterstitialFactory;
import com.skillz.mopub.mobileads.test.support.TestHtmlBannerWebViewFactory;
import com.skillz.mopub.mobileads.test.support.TestHtmlInterstitialWebViewFactory;
import com.skillz.mopub.mobileads.test.support.TestMoPubViewFactory;
import com.skillz.mopub.mobileads.test.support.TestMraidControllerFactory;
import com.skillz.mopub.mobileads.test.support.TestVastManagerFactory;
import com.skillz.mopub.nativeads.factories.CustomEventNativeFactory;
import com.skillz.mopub.nativeads.test.support.TestCustomEventNativeFactory;

import org.junit.runners.model.InitializationError;
import org.mockito.MockitoAnnotations;
import org.robolectric.DefaultTestLifecycle;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.TestLifecycle;
import org.robolectric.android.util.concurrent.RoboExecutorService;

import static com.skillz.mopub.common.MoPub.LocationAwareness;

public class SdkTestRunner extends RobolectricTestRunner {

    public SdkTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    @NonNull
    protected Class<? extends TestLifecycle> getTestLifecycleClass() {
        return TestLifeCycleWithInjection.class;
    }

    public static class TestLifeCycleWithInjection extends DefaultTestLifecycle {
        @Override
        public void prepareTest(Object test) {
            ClientMetadata.clearForTesting();

            // Precondition exceptions should not be thrown during tests so that we can test
            // for unexpected behavior even after failing a precondition.
            Preconditions.NoThrow.setStrictMode(false);

            DateAndTime.setInstance(new TestDateAndTime());
            CustomEventBannerFactory.setInstance(new TestCustomEventBannerFactory());
            CustomEventInterstitialFactory.setInstance(new TestCustomEventInterstitialFactory());
            CustomEventBannerAdapterFactory.setInstance(new TestCustomEventBannerAdapterFactory());
            MoPubViewFactory.setInstance(new TestMoPubViewFactory());
            CustomEventInterstitialAdapterFactory.setInstance(new TestCustomEventInterstitialAdapterFactory());
            HtmlBannerWebViewFactory.setInstance(new TestHtmlBannerWebViewFactory());
            HtmlInterstitialWebViewFactory.setInstance(new TestHtmlInterstitialWebViewFactory());
            AdViewControllerFactory.setInstance(new TestAdViewControllerFactory());
            VastManagerFactory.setInstance(new TestVastManagerFactory());
            MethodBuilderFactory.setInstance(new TestMethodBuilderFactory());
            CustomEventNativeFactory.setInstance(new TestCustomEventNativeFactory());
            MraidControllerFactory.setInstance(new TestMraidControllerFactory());

            ShadowAsyncTasks.reset();
            ShadowMoPubHttpUrlConnection.reset();
            ShadowReflection.reset();

            ShadowAvidAdSessionManager.reset();
            ShadowAvidManager.reset();
            ShadowMoatFactory.reset();

            MoPub.setLocationAwareness(LocationAwareness.NORMAL);
            MoPub.setLocationPrecision(6);

            MockitoAnnotations.initMocks(test);

            AsyncTasks.setExecutor(new RoboExecutorService());
            CacheService.clearAndNullCaches();
        }
    }
}
