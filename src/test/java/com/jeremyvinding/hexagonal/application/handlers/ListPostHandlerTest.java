package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ListPostHandlerTest {
  @Mock
  PostSecondaryPort secondaryPort;
  private ListPostHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new ListPostHandler(secondaryPort);
  }

  @Test
  void listPosts() throws Exception {
    var author = Author.of(UUID.randomUUID(), null);
    var listPosts = PostUseCase.list(author.getId());
    var post0 = Post.of(UUID.randomUUID(), "Post 0");
    var post1 = Post.of(UUID.randomUUID(), "Post 1");
    doReturn(List.of(post0, post1)).when(secondaryPort).list(eq(author));

    var results = handler.handle(author, listPosts);

    assertEquals(2, results.size());
    assertEquals(post0, results.get(0));
    assertEquals(post1, results.get(1));
  }
}
