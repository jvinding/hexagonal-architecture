package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ListAuthorHandlerTest {
  @Mock
  AuthorSecondaryPort secondaryPort;
  private ListAuthorsHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new ListAuthorsHandler(secondaryPort);
  }

  @Test
  void listAuthors() throws Exception {
    var author0 = Author.of(UUID.randomUUID(), "Author 0");
    var author1 = Author.of(UUID.randomUUID(), "Author 1");
    doReturn(List.of(author0, author1)).when(secondaryPort).list();

    var results = handler.handle(AuthorUseCase.list());

    assertEquals(2, results.size());
    assertEquals(author0, results.get(0));
    assertEquals(author1, results.get(1));
  }
}
