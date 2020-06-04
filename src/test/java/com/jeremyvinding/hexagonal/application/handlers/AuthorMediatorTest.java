package com.jeremyvinding.hexagonal.application.handlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthorMediatorTest {
  @Mock
  AuthorUseCaseHandlerRegistry registry;
  @Mock
  AuthorUseCaseHandler handler;
  private AuthorMediator mediator;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    doReturn(handler).when(registry).getHandler(any());
    mediator = new AuthorMediator(registry);
  }

  @Test
  void addAuthor() throws Exception {
    var useCase = AuthorUseCase.add(Author.of(UUID.randomUUID(), "Author"));
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(eq(useCase));
  }

  @Test
  void listAuthors() throws Exception {
    var useCase = AuthorUseCase.list();
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(eq(useCase));
  }

  @Test
  void getAuthor() throws Exception {
    var useCase = AuthorUseCase.get(UUID.randomUUID());
    mediator.execute(useCase);
    verify(registry, times(1)).getHandler(eq(useCase));
    verify(handler, times(1)).handle(eq(useCase));
  }
}
