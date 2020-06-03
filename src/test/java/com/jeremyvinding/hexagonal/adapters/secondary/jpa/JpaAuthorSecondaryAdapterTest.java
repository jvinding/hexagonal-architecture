package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.PersistenceException;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JpaAuthorSecondaryAdapterTest {
  @Mock
  JpaAuthorRepository repository;
  JpaAuthorSecondaryAdapter adapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    adapter = new JpaAuthorSecondaryAdapter(repository);
  }

  @Nested
  class Add {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), null);

      adapter.add(author);

      verify(repository, times(1)).save(eq(jpaAuthor));
    }

    @Test
    void exceptionIsWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doThrow(new PersistenceException()).when(repository).save(any());

      assertThrows(SecondaryPortException.class, () -> adapter.add(author));
    }
  }

  @Nested
  class ListAuthors {
    @Test
    void success() throws Exception {
      var author0 = Author.of(UUID.randomUUID(), "author 0");
      var jpaAuthor0 = new JpaAuthor(author0.getId(), author0.getName(), null);
      var author1 = Author.of(UUID.randomUUID(), "author 1");
      var jpaAuthor1 = new JpaAuthor(author1.getId(), author1.getName(), null);
      doReturn(List.of(jpaAuthor0, jpaAuthor1)).when(repository).findAll();

      var response = adapter.list();

      assertEquals(2, response.size());
      assertEquals(author0, response.get(0));
      assertEquals(author1, response.get(1));
    }

    @Test
    void exceptionIsWrapped() {
      doThrow(new PersistenceException()).when(repository).findAll();

      assertThrows(SecondaryPortException.class, () -> adapter.list());
    }
  }

  @Nested
  class GetAuthor {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author 0");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), null);
      doReturn(Optional.of(jpaAuthor)).when(repository).findById(author.getId());

      var response = adapter.get(author.getId());

      assertEquals(Optional.of(author), response);
    }

    @Test
    void noAuthor() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doReturn(Optional.empty()).when(repository).findById(author.getId());

      var response = adapter.get(author.getId());

      assertEquals(Optional.empty(), response);
    }

    @Test
    void exceptionIsWrapped() {
      doThrow(new PersistenceException()).when(repository).findById(any());

      assertThrows(SecondaryPortException.class, () -> adapter.get(UUID.randomUUID()));
    }
  }
}
