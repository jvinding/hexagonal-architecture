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
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PostHandlerTest {

  @Mock PostSecondaryPort secondaryPort;
  PostHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new PostHandler(secondaryPort);
  }

  @Test
  void addPost() throws Exception {
    var author = Author.of(UUID.randomUUID(), null);
    var post = Post.of(UUID.randomUUID(), "post");

    handler.execute(PostUseCase.add(author.getId(), post));

    verify(secondaryPort, times(1)).add(eq(author), eq(post));
  }

  @Test
  void listPosts() throws Exception {
    var authorId = UUID.randomUUID();
    var post0 = Post.of(UUID.randomUUID(), "Post 0");
    var post1 = Post.of(UUID.randomUUID(), "Post 1");
    doReturn(List.of(post0, post1)).when(secondaryPort).list(eq(Author.of(authorId, null)));

    var results = handler.execute(PostUseCase.list(authorId));

    assertEquals(2, results.size());
    assertEquals(post0, results.get(0));
    assertEquals(post1, results.get(1));
  }

  @Test
  void getPosts() throws Exception {
    var authorId = UUID.randomUUID();
    var id = UUID.randomUUID();
    var post = Post.of(id, "Post");
    doReturn(Optional.of(post)).when(secondaryPort).get(eq(Author.of(authorId, null)), eq(id));

    var resultOptional = handler.execute(PostUseCase.get(authorId, id));

    assertTrue(resultOptional.isPresent());
    assertEquals(post, resultOptional.get());
  }
}
