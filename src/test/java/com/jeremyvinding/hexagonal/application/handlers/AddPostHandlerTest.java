package com.jeremyvinding.hexagonal.application.handlers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AddPostHandlerTest {
  @Mock
  PostSecondaryPort secondaryPort;
  private AddPostHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new AddPostHandler(secondaryPort);
  }

  @Test
  void addPost() throws Exception {
    var author = Author.of(UUID.randomUUID(), null);
    var post = Post.of(UUID.randomUUID(), "Post");
    var addPost = PostUseCase.add(author.getId(), post);

    handler.handle(author, addPost);

    verify(secondaryPort, times(1)).add(eq(author), eq(post));
  }
}
