package com.jeremyvinding.hexagonal.ports.primary;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface PostPrimaryPort {
  <T extends PostUseCase<R>, R> R execute(T useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException;
}
