package com.jeremyvinding.hexagonal.ports.primary;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface AuthorPrimaryPort {
  <T extends AuthorUseCase<R>, R> R execute(T useCase) throws DomainException, SecondaryPortException;
}
