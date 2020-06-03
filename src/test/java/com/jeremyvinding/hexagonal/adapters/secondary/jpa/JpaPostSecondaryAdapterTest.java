package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class JpaPostSecondaryAdapterTest {
  @Mock
  JpaAuthorRepository authorRepository;
  @Mock
  JpaPostRepository postRepository;
  JpaPostSecondaryAdapter adapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    adapter = new JpaPostSecondaryAdapter(authorRepository, postRepository);
  }

  @Nested
  class Add {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(author.getId());

      var post = Post.of(UUID.randomUUID(), "post");
      var jpaPost = new JpaPost(post.getId(), jpaAuthor, post.getBody());

      adapter.add(author, post);

      verify(postRepository, times(1)).save(eq(jpaPost));
    }

    @Test
    void noAuthor() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doReturn(Optional.empty()).when(authorRepository).findById(any());

      var post = Post.of(UUID.randomUUID(), "post");

      assertThrows(MissingAuthorException.class, () -> adapter.add(author, post));
    }

    @Test
    void authorRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doThrow(new PersistenceException("boom")).when(authorRepository).findById(any());

      var post = Post.of(UUID.randomUUID(), "post");

      assertThrows(SecondaryPortException.class, () -> adapter.add(author, post));
    }

    @Test
    void postRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(author.getId());

      var post = Post.of(UUID.randomUUID(), "post");
      doThrow(new PersistenceException("boom")).when(postRepository).save(any());

      assertThrows(SecondaryPortException.class, () -> adapter.add(author, post));
    }
  }

  @Nested
  class ListPosts {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      var post0 = Post.of(UUID.randomUUID(), "post 0");
      var post1 = Post.of(UUID.randomUUID(), "post 1");
      var jpaPost0 = new JpaPost(post0.getId(), jpaAuthor, post0.getBody());
      var jpaPost1 = new JpaPost(post1.getId(), jpaAuthor, post1.getBody());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(any());
      doReturn(List.of(jpaPost0, jpaPost1)).when(postRepository).findAllByAuthor(eq(jpaAuthor));

      var posts = adapter.list(author);

      assertEquals(2, posts.size());
      assertEquals(post0, posts.get(0));
      assertEquals(post1, posts.get(1));
    }

    @Test
    void noAuthor() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doReturn(Optional.empty()).when(authorRepository).findById(any());
      assertThrows(MissingAuthorException.class, () -> adapter.list(author));
    }

    @Test
    void authorRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doThrow(new PersistenceException("boom")).when(authorRepository).findById(any());

      assertThrows(SecondaryPortException.class, () -> adapter.list(author));
    }

    @Test
    void postRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(author.getId());

      doThrow(new PersistenceException("boom")).when(postRepository).findAllByAuthor(any());

      assertThrows(SecondaryPortException.class, () -> adapter.list(author));
    }
  }

  @Nested
  class GetPost {
    @Test
    void success() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      var post = Post.of(UUID.randomUUID(), "post 0");
      var jpaPost = new JpaPost(post.getId(), jpaAuthor, post.getBody());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(any());
      doReturn(Optional.of(jpaPost)).when(postRepository).findByAuthorAndId(eq(jpaAuthor), eq(post.getId()));

      var result = adapter.get(author, post.getId());

      assertTrue(result.isPresent());
      assertEquals(post, result.get());
    }

    @Test
    void noAuthor() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doReturn(Optional.empty()).when(authorRepository).findById(any());
      assertThrows(MissingAuthorException.class, () -> adapter.get(author, UUID.randomUUID()));
    }

    @Test
    void authorRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      doThrow(new PersistenceException("boom")).when(authorRepository).findById(any());

      assertThrows(SecondaryPortException.class, () -> adapter.get(author, UUID.randomUUID()));
    }

    @Test
    void postRepositoryExceptionsGetWrapped() throws Exception {
      var author = Author.of(UUID.randomUUID(), "author");
      var jpaAuthor = new JpaAuthor(author.getId(), author.getName(), List.of());
      doReturn(Optional.of(jpaAuthor)).when(authorRepository).findById(author.getId());

      doThrow(new PersistenceException("boom")).when(postRepository).findByAuthorAndId(any(), any());

      assertThrows(SecondaryPortException.class, () -> adapter.get(author, UUID.randomUUID()));
    }
  }
}
