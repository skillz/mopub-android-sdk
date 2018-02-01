package com.skillz.mopub.mobileads;

import com.skillz.mopub.common.test.support.SdkTestRunner;
import com.skillz.mopub.mobileads.BuildConfig;
import com.skillz.mopub.mobileads.test.support.VastUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SdkTestRunner.class)
@Config(constants = BuildConfig.class)
public class VastExtensionXmlManagerTest {
    private VastExtensionXmlManager subject;

    @Test
    public void getType_shouldReturnExtensionType() throws Exception {
        String extensionXml = "<Extension type=\"MyExtensionType\"></Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));

        assertThat(subject.getType()).isEqualTo("MyExtensionType");
    }

    @Test
    public void getVideoViewabilityTracker_shouldReturnVideoViewabilityTracker() throws Exception {
        String extensionXml = "<Extension type=\"MoPub\">" +
                "                  <MoPubViewabilityTracker" +
                "                          viewablePlaytime=\"2.5\"" +
                "                          percentViewable=\"50%\">" +
                "                      <![CDATA[https://ad.server.com/impression/dot.gif]]>" +
                "                  </MoPubViewabilityTracker>" +
                "              </Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));
        VideoViewabilityTracker tracker = subject.getVideoViewabilityTracker();

        assertThat(tracker).isNotNull();
        assertThat(tracker.getViewablePlaytimeMS()).isEqualTo(2500);
        assertThat(tracker.getPercentViewable()).isEqualTo(50);
        assertThat(tracker.getContent()).isEqualTo("https://ad.server.com/impression/dot.gif");
    }

    @Test
    public void getVideoViewabilityTracker_withoutViewabilityTracker_shouldReturnNull() throws Exception {
        String extensionXml = "<Extension type=\"MoPub\"></Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));
        VideoViewabilityTracker tracker = subject.getVideoViewabilityTracker();

        assertThat(tracker).isNull();
    }

    @Test
    public void getVideoViewabilityTracker_withoutVieweablePlaytime_shouldReturnNull() throws Exception {
        String extensionXml = "<Extension type=\"MoPub\">" +
                "                  <MoPubViewabilityTracker" +
                "                          percentViewable=\"50%\">" +
                "                      <![CDATA[https://ad.server.com/impression/dot.gif]]>" +
                "                  </MoPubViewabilityTracker>" +
                "              </Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));
        VideoViewabilityTracker tracker = subject.getVideoViewabilityTracker();

        assertThat(tracker).isNull();
    }

    @Test
    public void getVideoViewabilityTracker_withoutPercentViewable_shouldReturnNull() throws Exception {
        String extensionXml = "<Extension type=\"MoPub\">" +
                "                  <MoPubViewabilityTracker" +
                "                          viewablePlaytime=\"2.5\">" +
                "                      <![CDATA[https://ad.server.com/impression/dot.gif]]>" +
                "                  </MoPubViewabilityTracker>" +
                "              </Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));
        VideoViewabilityTracker tracker = subject.getVideoViewabilityTracker();

        assertThat(tracker).isNull();
    }

    @Test
    public void getVideoViewabilityTracker_withoutTrackerUrl_shouldReturnNull() throws Exception {
        String extensionXml = "<Extension type=\"MoPub\">" +
                "                  <MoPubViewabilityTracker" +
                "                          viewablePlaytime=\"2.5\"" +
                "                          percentViewable=\"50%\">" +
                "                  </MoPubViewabilityTracker>" +
                "              </Extension>";

        subject = new VastExtensionXmlManager(VastUtils.createNode(extensionXml));
        VideoViewabilityTracker tracker = subject.getVideoViewabilityTracker();

        assertThat(tracker).isNull();
    }

}
