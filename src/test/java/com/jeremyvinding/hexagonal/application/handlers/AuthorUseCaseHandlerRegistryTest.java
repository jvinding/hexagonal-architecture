package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthorUseCaseHandlerRegistryTest {
  @Mock
  AuthorSecondaryPort secondaryPort;
  private AuthorUseCaseHandlerRegistry registry;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    registry = new AuthorUseCaseHandlerRegistry(secondaryPort);
  }

  @Test
  void addAuthor() throws Exception {
    var author = Author.of(UUID.randomUUID(), "Author");
    var handler = registry.getHandler(AuthorUseCase.add(author));
    assertTrue(handler instanceof AddAuthorHandler);
  }

  @Test
  void listAuthors() {
    var handler = registry.getHandler(AuthorUseCase.list());
    assertTrue(handler instanceof ListAuthorsHandler);
  }

  @Test
  void getAuthor() {
    var handler = registry.getHandler(AuthorUseCase.get(UUID.randomUUID()));
    assertTrue(handler instanceof GetAuthorHandler);
  }

  @Test
  void getInvalid() {
    assertThrows(UnsupportedOperationException.class, () -> registry.getHandler(new AuthorUseCase<>() {
    }));
  }
}
