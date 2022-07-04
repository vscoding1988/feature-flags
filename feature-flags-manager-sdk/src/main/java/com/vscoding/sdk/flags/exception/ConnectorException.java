package com.vscoding.sdk.flags.exception;

/**
 * Exception for connection errors
 */
public class ConnectorException extends RuntimeException{

  public ConnectorException(String message, Throwable cause) {
    super(message, cause);
  }
}
