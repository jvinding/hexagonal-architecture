package com.jeremyvinding.hexagonal.ports.secondary;

import java.util.UUID;

public class MissingAuthorException extends Exception {

  public MissingAuthorException(UUID authorId, String message) {
    super("No author with the id '" + authorId + "' exists: " + message);
  }
}
