package com.jeremyvinding.hexagonal.ports.primary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface AuthorPrimaryPort {
  void add(Author author) throws DomainException, SecondaryPortException;

  List<Author> list() throws DomainException, SecondaryPortException;

  Optional<Author> get(UUID authorId) throws DomainException, SecondaryPortException;
}
