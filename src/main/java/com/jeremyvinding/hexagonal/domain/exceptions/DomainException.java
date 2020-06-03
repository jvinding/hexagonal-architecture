package com.jeremyvinding.hexagonal.domain.exceptions;

public class DomainException extends Exception {
  public DomainException(String message) {
    super(message);
  }
}
