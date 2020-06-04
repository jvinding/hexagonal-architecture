package com.jeremyvinding.hexagonal.ports.primary;

import java.util.List;
import java.util.Optional;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AddAuthor;
import com.jeremyvinding.hexagonal.domain.usecases.GetAuthor;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface AuthorPrimaryPort {
  void add(AddAuthor useCase) throws DomainException, SecondaryPortException;

  List<Author> list() throws DomainException, SecondaryPortException;

  Optional<Author> get(GetAuthor useCase) throws DomainException, SecondaryPortException;
}
