package com.skillz.mopub.nativeads.factories;

import com.skillz.mopub.mobileads.BuildConfig;
import com.skillz.mopub.nativeads.CustomEventNative;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.fail;


@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class CustomEventNativeFactoryTest {

    @Before
    public void setUp() {
        CustomEventNativeFactory.setInstance(new CustomEventNativeFactory());
    }

    @Test
    public void create_withValidClassName_shouldCreateClass() throws Exception {
        assertCustomEventClassCreated("com.skillz.mopub.nativeads.MoPubCustomEventNative");
    }

    @Test
    public void create_withInvalidClassName_shouldThrowException() throws Exception {
        try {
            CustomEventNativeFactory.create("com.skillz.mopub.nativeads.inVaLiDClassssssName1231232131");
            fail("CustomEventNativeFactory did not throw exception on create");
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    public void create_withNullClassName_shouldReturnMoPubCustomEventNativeClass() throws Exception {
        assertThat(CustomEventNativeFactory.create(null).getClass().getName()).isEqualTo("com.skillz.mopub.nativeads.MoPubCustomEventNative");
    }

    private void assertCustomEventClassCreated(final String className) throws Exception {
        final CustomEventNative customEventNative = CustomEventNativeFactory.create(className);
        assertThat(customEventNative.getClass().getName()).isEqualTo(className);
    }
}