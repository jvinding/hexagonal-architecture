package com.jeremyvinding.hexagonal.adapters.primary.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.primary.AuthorPrimaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class RestAuthorPrimaryAdapterTest {
  @Mock
  AuthorPrimaryPort primaryPort;
  RestAuthorPrimaryAdapter adapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    adapter = new RestAuthorPrimaryAdapter(primaryPort);
  }

  private void assertAuthorEquals(Author author, RestAuthor restAuthor) {
    assertNotNull(author);
    assertNotNull(restAuthor);
    assertEquals(author.getId(), restAuthor.getId());
    assertEquals(author.getName(), restAuthor.getName());
  }

  @Nested
  class ListAuthors {
    @Test
    void success() throws Exception {
      var author0 = Author.of(UUID.randomUUID(), "Author 0");
      var author1 = Author.of(UUID.randomUUID(), "Author 1");
      doReturn(List.of(author0, author1)).when(primaryPort).list();

      var response = adapter.listAuthors();

      assertEquals(2, response.size());
      assertAuthorEquals(author0, response.get(0));
      assertAuthorEquals(author1, response.get(1));
    }

    @Test
    void noResults() throws Exception {
      doReturn(List.of()).when(primaryPort).list();

      var response = adapter.listAuthors();

      assertEquals(0, response.size());
    }
  }

  @Nested
  class GetAuthor {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "Author 0");
      doReturn(Optional.of(author)).when(primaryPort).get(eq(AuthorUseCase.get(author.getId())));

      var response = adapter.getAuthor(author.getId());

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertAuthorEquals(author, response.getBody());
    }

    @Test
    void noResults() throws Exception {
      doReturn(Optional.empty()).when(primaryPort).get(any());

      var response = adapter.getAuthor(UUID.randomUUID());

      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
  }

  @Nested
  class AddAuthor {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "Author 0");

      var response = adapter.addAuthor(author.getId(), author.getName());

      verify(primaryPort, times(1)).add(eq(AuthorUseCase.add(author)));
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
  }
}
