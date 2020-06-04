package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface AuthorUseCaseHandler<UseCaseType extends AuthorUseCase<?>, ResultType> {
  ResultType handle(UseCaseType useCase) throws DomainException, SecondaryPortException;
}
