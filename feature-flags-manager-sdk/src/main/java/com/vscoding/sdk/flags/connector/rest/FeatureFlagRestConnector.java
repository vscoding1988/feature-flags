package com.vscoding.sdk.flags.connector.rest;

import com.google.gson.Gson;
import com.vscoding.sdk.flags.connector.FeatureFlagConnectorBase;
import com.vscoding.sdk.flags.exception.ConnectorException;
import com.vscoding.sdk.flags.bean.FeatureFlag;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagRestConnector extends FeatureFlagConnectorBase {

  private final String domain;
  private final HttpClient client;

  public FeatureFlagRestConnector(
          @Value("${ffm.connector.domain}") String domain,
          @Value("${ffm.connector.applicationKey}") String applicationKey) {
    super();
    this.domain = domain;
    this.client = HttpClientBuilder.create()
            .setDefaultHeaders(
                    Collections.singleton(new BasicHeader("Authorization", applicationKey)))
            .build();
  }

  @Override
  protected List<FeatureFlag> getFeatureFlags() {
    var get = new HttpGet(domain + "/api/fmt/getAll");

    try {
      var response = client.execute(get);

      try (var reader = new InputStreamReader(response.getEntity().getContent())) {
        var flags = new Gson().fromJson(reader, FeatureFlag[].class);

        return Arrays.asList(flags);
      }
    } catch (Exception e) {
      throw new ConnectorException("Error connecting to:" + domain, e);
    }
  }
}
