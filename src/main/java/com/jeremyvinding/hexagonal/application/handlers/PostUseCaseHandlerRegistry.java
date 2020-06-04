package com.jeremyvinding.hexagonal.application.handlers;

import java.util.HashMap;
import java.util.function.Supplier;

import com.jeremyvinding.hexagonal.domain.usecases.AddPost;
import com.jeremyvinding.hexagonal.domain.usecases.GetPost;
import com.jeremyvinding.hexagonal.domain.usecases.ListPosts;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class PostUseCaseHandlerRegistry {

  private final PostSecondaryPort secondaryPort;
  private final HashMap<Class<? extends PostUseCase<?>>, Supplier<? extends PostUseCaseHandler>> registry = new HashMap<>();

  PostUseCaseHandlerRegistry(PostSecondaryPort postSecondaryPort) {
    this.secondaryPort = postSecondaryPort;
    registry.put(AddPost.class, () -> new AddPostHandler(postSecondaryPort));
    registry.put(ListPosts.class, () -> new ListPostHandler(postSecondaryPort));
    registry.put(GetPost.class, () -> new GetPostHandler(postSecondaryPort));
  }

  <UseCaseType extends PostUseCase<ResultType>, UseCaseHandlerType extends PostUseCaseHandler<UseCaseType, ResultType>, ResultType> UseCaseHandlerType getHandler(
      UseCaseType useCase) {
    var handlerSupplier = registry.get(useCase.getClass());
    if (null == handlerSupplier) {
      throw new UnsupportedOperationException(useCase.getClass().getSimpleName());
    }

    return (UseCaseHandlerType) handlerSupplier.get();
  }
}
