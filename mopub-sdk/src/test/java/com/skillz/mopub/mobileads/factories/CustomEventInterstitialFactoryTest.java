package com.mopub.mobileads.factories;

import com.mopub.common.test.support.SdkTestRunner;
import com.skillz.mopub.mobileads.AdTypeTranslator;
import com.mopub.mobileads.BuildConfig;
import com.skillz.mopub.mobileads.CustomEventInterstitial;
import com.skillz.mopub.mobileads.factories.CustomEventInterstitialFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.skillz.mopub.mobileads.AdTypeTranslator.CustomEventType.HTML_INTERSTITIAL;
import static com.skillz.mopub.mobileads.AdTypeTranslator.CustomEventType.MRAID_INTERSTITIAL;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SdkTestRunner.class)
@Config(constants = BuildConfig.class)
public class CustomEventInterstitialFactoryTest {

    private CustomEventInterstitialFactory subject;

    @Before
    public void setup() {
        subject = new CustomEventInterstitialFactory();
    }

    @Test
    public void create_shouldCreateInterstitials() throws Exception {
        assertCustomEventClassCreated(MRAID_INTERSTITIAL);
        assertCustomEventClassCreated(HTML_INTERSTITIAL);
    }

    private void assertCustomEventClassCreated(AdTypeTranslator.CustomEventType customEventType) throws Exception {
        CustomEventInterstitial customEventInterstitial = subject.internalCreate(customEventType.toString());
        assertThat(customEventInterstitial.getClass().getName()).isEqualTo(customEventType.toString());
    }
}
