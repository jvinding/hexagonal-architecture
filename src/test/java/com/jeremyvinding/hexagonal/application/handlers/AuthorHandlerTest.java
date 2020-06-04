package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthorHandlerTest {

  @Mock
  AuthorSecondaryPort secondaryPort;
  AuthorHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new AuthorHandler(secondaryPort);
  }

  @Test
  void addAuthor() throws Exception {
    var author = Author.of(UUID.randomUUID(), "Author");
    handler.execute(AuthorUseCase.add(author));
    verify(secondaryPort, times(1)).add(eq(author));
  }

  @Test
  void listAuthors() throws Exception {
    var author0 = Author.of(UUID.randomUUID(), "Author 0");
    var author1 = Author.of(UUID.randomUUID(), "Author 1");
    doReturn(List.of(author0, author1)).when(secondaryPort).list();

    var results = handler.execute(AuthorUseCase.list());

    assertEquals(2, results.size());
    assertEquals(author0, results.get(0));
    assertEquals(author1, results.get(1));
  }

  @Test
  void getAuthor() throws Exception {
    var authorId = UUID.randomUUID();
    var name = "Author";
    var author = Author.of(authorId, name);
    doReturn(Optional.of(author)).when(secondaryPort).get(authorId);

    var resultOptional = handler.execute(AuthorUseCase.get(authorId));

    assertTrue(resultOptional.isPresent());
    var result = resultOptional.get();
    assertEquals(author, result);
  }
}
