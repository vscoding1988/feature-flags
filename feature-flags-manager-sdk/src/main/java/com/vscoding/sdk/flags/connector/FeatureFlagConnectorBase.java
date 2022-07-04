package com.vscoding.sdk.flags.connector;

import com.vscoding.sdk.flags.bean.FeatureFlag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This service will load the feature flags asynchronously, this should make sure, that the code
 */
public abstract class FeatureFlagConnectorBase implements FeatureFlagConnector {

  private final Map<String, FeatureFlag> cache = new HashMap<>();

  protected FeatureFlagConnectorBase() {
  }

  /**
   * Execute featureFlags update after Constructor every X seconds
   */
  @PostConstruct
  @Scheduled(fixedRateString = "${ffm.connector.cacheInSeconds:360}", fixedDelayString = "${ffm.connector.cacheInSeconds:360}", timeUnit = TimeUnit.SECONDS)
  protected void update() {
    var featureFlags = getFeatureFlags();
    cache.clear();

    featureFlags.forEach(flag -> cache.put(flag.getName(), flag));
  }

  @Override
  public Optional<FeatureFlag> getFeatureFlag(String featureFlagName) {
    if (cache.containsKey(featureFlagName)) {
      return Optional.of(cache.get(featureFlagName));
    }
    return Optional.empty();
  }

  protected abstract List<FeatureFlag> getFeatureFlags();
}
