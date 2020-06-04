package com.jeremyvinding.hexagonal.application.handlers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AddAuthorHandlerTest {
  @Mock
  AuthorSecondaryPort secondaryPort;
  private AddAuthorHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new AddAuthorHandler(secondaryPort);
  }

  @Test
  void addAuthor() throws Exception {
    var author = Author.of(UUID.randomUUID(), "Author");
    var addPost = AuthorUseCase.add(author);
    handler.handle(addPost);
    verify(secondaryPort, times(1)).add(eq(author));
  }
}
