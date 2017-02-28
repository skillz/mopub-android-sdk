package com.skillz.mopub.mobileads.factories;

import com.skillz.mopub.common.test.support.SdkTestRunner;
import com.skillz.mopub.mobileads.AdTypeTranslator;
import com.skillz.mopub.mobileads.BuildConfig;
import com.skillz.mopub.mobileads.CustomEventBanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static com.skillz.mopub.mobileads.AdTypeTranslator.CustomEventType.HTML_BANNER;
import static com.skillz.mopub.mobileads.AdTypeTranslator.CustomEventType.MRAID_BANNER;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SdkTestRunner.class)
@Config(constants = BuildConfig.class)
public class CustomEventBannerFactoryTest {

    private CustomEventBannerFactory subject;

    @Before
    public void setup() {
        subject = new CustomEventBannerFactory();
    }

    @Test
    public void create_shouldCreateBanners() throws Exception {
        assertCustomEventClassCreated(MRAID_BANNER);
        assertCustomEventClassCreated(HTML_BANNER);
    }

    private void assertCustomEventClassCreated(AdTypeTranslator.CustomEventType customEventType) throws Exception {
        CustomEventBanner customEventBanner = subject.internalCreate(customEventType.toString());
        assertThat(customEventBanner.getClass().getName()).isEqualTo(customEventType.toString());
    }
}
