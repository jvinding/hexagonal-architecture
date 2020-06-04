package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.primary.AuthorPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.stereotype.Component;

@Component
public class AuthorMediator implements AuthorPrimaryPort {
  private final AuthorUseCaseHandlerRegistry handlerRegistry;

  AuthorMediator(AuthorUseCaseHandlerRegistry handlerRegistry) {
    this.handlerRegistry = handlerRegistry;
  }

  @Override
  public <T extends AuthorUseCase<R>, R> R execute(T useCase) throws DomainException, SecondaryPortException {
    return handlerRegistry.getHandler(useCase).handle(useCase);
  }
}
