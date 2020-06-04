package com.jeremyvinding.hexagonal.application.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PostMediatorTest {
  @Mock
  PostUseCaseHandlerRegistry registry;
  @Mock
  PostUseCaseHandler handler;
  private PostMediator mediator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    doReturn(handler).when(registry).getHandler(any());
    mediator = new PostMediator(registry);
  }

  @Test
  void addPost() throws Exception {
    var useCase = PostUseCase.add(UUID.randomUUID(), Post.of(UUID.randomUUID(), "Post"));
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(any(Author.class), eq(useCase));
  }

  @Test
  void listPosts() throws Exception {
    var useCase = PostUseCase.list(UUID.randomUUID());
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(any(Author.class), eq(useCase));
  }

  @Test
  void getPost() throws Exception {
    var useCase = PostUseCase.get(UUID.randomUUID(), UUID.randomUUID());
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(any(Author.class), eq(useCase));
  }
}
