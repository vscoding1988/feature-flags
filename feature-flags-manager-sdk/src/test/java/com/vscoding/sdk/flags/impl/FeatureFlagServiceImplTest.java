package com.vscoding.sdk.flags.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import com.vscoding.sdk.flags.connector.FeatureFlagConnector;
import java.util.Optional;
import com.vscoding.sdk.flags.bean.FeatureFlag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeatureFlagServiceImplTest {

  private static final String EXISTS_ON = "exists.on";
  private static final String EXISTS_OFF = "exists.off";
  private static final String NOT_EXISTS = "not-exists";

  FeatureFlagConnector connector;

  @BeforeEach
  void setUp() {
    connector = mock(FeatureFlagConnector.class);
    when(connector.getFeatureFlag(EXISTS_ON)).thenReturn(Optional.of(new FeatureFlag("", true)));
    when(connector.getFeatureFlag(EXISTS_OFF)).thenReturn(Optional.of(new FeatureFlag("", false)));
    when(connector.getFeatureFlag(NOT_EXISTS)).thenReturn(Optional.empty());
  }

  @Test
  void isOn() {
    // When
    var sut = new FeatureFlagServiceImpl(connector, false, true);

    // Then
    assertTrue(sut.isOn(EXISTS_ON));
    assertFalse(sut.isOn(EXISTS_OFF));
    assertFalse(sut.isOn(NOT_EXISTS), "Default value is not read.");
    assertTrue(sut.isOn(NOT_EXISTS, true), "Provided default value is not read.");
  }

  @Test
  void isOff() {
    // When
    var sut = new FeatureFlagServiceImpl(connector, false, true);

    // Then
    assertFalse(sut.isOff(EXISTS_ON));
    assertTrue(sut.isOff(EXISTS_OFF));
    assertTrue(sut.isOff(NOT_EXISTS), "Default value is not read.");
    assertFalse(sut.isOff(NOT_EXISTS, false), "Provided default value is not read.");
  }
}
