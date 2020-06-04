package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface PostUseCaseHandler<UseCaseType extends PostUseCase<?>, ResultType> {
  ResultType handle(Author author, UseCaseType useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException;
}
