package org.whispersystems.textsecuregcm.storage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.whispersystems.textsecuregcm.configuration.dynamic.DynamicConfiguration;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.appconfig.AppConfigClient;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationRequest;
import software.amazon.awssdk.services.appconfig.model.GetConfigurationResponse;
import software.amazon.awssdk.services.appconfigdata.AppConfigDataClient;

public class DynamicConfigurationManagerTest {

  private DynamicConfigurationManager<DynamicConfiguration> dynamicConfigurationManager;
  private AppConfigClient             appConfig;

  @Before
  public void setup() {
    this.appConfig                   = mock(AppConfigClient.class);
    AppConfigDataClient appConfigDataClient = mock(AppConfigDataClient.class);
    this.dynamicConfigurationManager = new DynamicConfigurationManager<>(appConfig, appConfigDataClient, "foo", "bar", "baz", "poof", DynamicConfiguration.class);
  }

  @Test
  public void testGetConfig() {
    ArgumentCaptor<GetConfigurationRequest> captor = ArgumentCaptor.forClass(GetConfigurationRequest.class);
    when(appConfig.getConfiguration(captor.capture())).thenReturn(
        GetConfigurationResponse.builder().content(SdkBytes.fromByteArray("test: true".getBytes())).configurationVersion("1").build());

    dynamicConfigurationManager.start();

    assertThat(captor.getValue().application()).isEqualTo("foo");
    assertThat(captor.getValue().environment()).isEqualTo("bar");
    assertThat(captor.getValue().configuration()).isEqualTo("baz");
    assertThat(captor.getValue().clientId()).isEqualTo("poof");

    assertThat(dynamicConfigurationManager.getConfiguration()).isNotNull();
  }
}
