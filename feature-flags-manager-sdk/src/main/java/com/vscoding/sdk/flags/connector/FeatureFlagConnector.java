package com.vscoding.sdk.flags.connector;


import java.util.Optional;
import com.vscoding.sdk.flags.bean.FeatureFlag;

public interface FeatureFlagConnector {

  /**
   * Request feature flag from the feature flag service
   *
   * @param featureFlagName name of the feature flag
   * @return optional of {@link FeatureFlag}
   */
  Optional<FeatureFlag> getFeatureFlag(String featureFlagName);
}
