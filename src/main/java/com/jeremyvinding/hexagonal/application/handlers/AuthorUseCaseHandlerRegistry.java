package com.jeremyvinding.hexagonal.application.handlers;

import java.util.HashMap;
import java.util.function.Supplier;

import com.jeremyvinding.hexagonal.domain.usecases.AddAuthor;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.domain.usecases.GetAuthor;
import com.jeremyvinding.hexagonal.domain.usecases.ListAuthors;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class AuthorUseCaseHandlerRegistry {
  private final AuthorSecondaryPort secondaryPort;
  private final HashMap<Class<? extends AuthorUseCase<?>>, Supplier<? extends AuthorUseCaseHandler>> registry = new HashMap<>();

  AuthorUseCaseHandlerRegistry(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
    registry.put(AddAuthor.class, () -> new AddAuthorHandler(secondaryPort));
    registry.put(ListAuthors.class, () -> new ListAuthorsHandler(secondaryPort));
    registry.put(GetAuthor.class, () -> new GetAuthorHandler(secondaryPort));
  }

  <UseCaseType extends AuthorUseCase<ResultType>, UseCaseHandlerType extends AuthorUseCaseHandler<UseCaseType, ResultType>, ResultType> UseCaseHandlerType getHandler(
      UseCaseType useCase) {
    var handlerSupplier = registry.get(useCase.getClass());
    if (null == handlerSupplier) {
      throw new UnsupportedOperationException(useCase.getClass().getSimpleName());
    }

    return (UseCaseHandlerType) handlerSupplier.get();
  }
}
