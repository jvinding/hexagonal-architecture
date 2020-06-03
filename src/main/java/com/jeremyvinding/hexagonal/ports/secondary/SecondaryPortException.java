package com.jeremyvinding.hexagonal.ports.secondary;

public class SecondaryPortException extends Exception {
  public SecondaryPortException(String message, Throwable cause) {
    super(message, cause);
  }
}
