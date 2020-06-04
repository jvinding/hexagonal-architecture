package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.primary.PostPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class PostMediator implements PostPrimaryPort {

  private final PostUseCaseHandlerRegistry handlerRegistry;

  PostMediator(PostUseCaseHandlerRegistry handlerRegistry) {
    this.handlerRegistry = handlerRegistry;
  }

  @Override
  public <T extends PostUseCase<R>, R> R execute(T useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    return handlerRegistry.getHandler(useCase).handle(getAuthor(useCase), useCase);
  }

  private Author getAuthor(PostUseCase<?> useCase) throws DomainException {
    return Author.of(useCase.getAuthorId(), null);
  }
}
