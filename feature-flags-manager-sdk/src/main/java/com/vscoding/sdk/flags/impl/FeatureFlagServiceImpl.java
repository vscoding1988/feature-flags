package com.vscoding.sdk.flags.impl;

import com.vscoding.sdk.flags.connector.FeatureFlagConnector;
import com.vscoding.sdk.flags.FeatureFlagService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagServiceImpl implements FeatureFlagService {

  private final FeatureFlagConnector connector;
  private final boolean defaultOnBehavior;
  private final boolean defaultOffBehavior;

  public FeatureFlagServiceImpl(FeatureFlagConnector connector,
          @Value("${ffm.on.default:false}") boolean defaultOnBehavior,
          @Value("${ffm.off.default:true}") boolean defaultOffBehavior) {
    this.connector = connector;
    this.defaultOnBehavior = defaultOnBehavior;
    this.defaultOffBehavior = defaultOffBehavior;
  }

  @Override
  public boolean isOn(String featureFlagName) {
    return isOn(featureFlagName, defaultOnBehavior);
  }

  @Override
  public boolean isOff(String featureFlagName) {
    return !isOn(featureFlagName, !defaultOffBehavior);
  }

  @Override
  public boolean isOff(String featureFlagName, boolean defaultValue) {
    return !isOn(featureFlagName, !defaultValue);
  }

  @Override
  public boolean isOn(String featureFlagName, boolean defaultValue) {
    var featureFlag = connector.getFeatureFlag(featureFlagName);

    if (featureFlag.isEmpty()) {
      return defaultValue;
    }

    return featureFlag.get().isActive();
  }
}
