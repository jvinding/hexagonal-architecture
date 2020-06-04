package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GetAuthorHandlerTest {
  @Mock
  AuthorSecondaryPort secondaryPort;
  private GetAuthorHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new GetAuthorHandler(secondaryPort);
  }

  @Test
  void getAuthor() throws Exception {
    var authorId = UUID.randomUUID();
    var name = "Author";
    var author = Author.of(authorId, name);
    doReturn(Optional.of(author)).when(secondaryPort).get(authorId);

    var resultOptional = handler.handle(AuthorUseCase.get(authorId));

    assertTrue(resultOptional.isPresent());
    var result = resultOptional.get();
    assertEquals(author, result);
  }
}
