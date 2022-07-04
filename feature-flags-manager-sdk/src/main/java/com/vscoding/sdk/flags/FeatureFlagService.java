package com.vscoding.sdk.flags;

public interface FeatureFlagService {

  /**
   * Method to get state of the feature flag.
   *
   * @param featureFlagName name of the feature flag
   * @return true if feature flag is on, when feature flag is not found, the response is false
   */
  boolean isOn(String featureFlagName);

  /**
   * Method to get state of the feature flag.
   *
   * @param featureFlagName name of the feature flag
   * @param defaultValue    define the value if feature flag is not present
   * @return true if feature flag is on, when feature flag is not found, the response is false
   */
  boolean isOn(String featureFlagName, boolean defaultValue);

  /**
   * Method to get state of the feature flag.
   *
   * @param featureFlagName name of the feature flag
   * @return true if feature flag is off or was not found
   */
  boolean isOff(String featureFlagName);

  /**
   * Method to get state of the feature flag.
   *
   * @param featureFlagName name of the feature flag
   * @param defaultValue    define the value if feature flag is not present
   * @return true if feature flag is off
   */
  boolean isOff(String featureFlagName, boolean defaultValue);
}
