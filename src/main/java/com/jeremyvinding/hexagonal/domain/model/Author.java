package com.jeremyvinding.hexagonal.domain.model;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {
  private static final String FORBIDDEN_NAME = "Bad Name";

  @NonNull
  UUID id;
  String name;

  public static Author of(UUID id, String name) throws DomainException {
    if (FORBIDDEN_NAME.equals(name)) {
      throw new DomainException("Name must not be " + name);
    }
    return new Author(id, name);
  }
}
