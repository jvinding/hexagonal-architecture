package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PostUseCaseHandlerRegistryTest {
  @Mock
  PostSecondaryPort secondaryPort;
  private PostUseCaseHandlerRegistry registry;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    registry = new PostUseCaseHandlerRegistry(secondaryPort);
  }

  @Test
  void addPost() {
    var handler = registry.getHandler(PostUseCase.add(UUID.randomUUID(), Post.of(UUID.randomUUID(), "Post")));
    assertTrue(handler instanceof AddPostHandler);
  }

  @Test
  void listPost() {
    var handler = registry.getHandler(PostUseCase.list(UUID.randomUUID()));
    assertTrue(handler instanceof ListPostHandler);
  }

  @Test
  void getPost() {
    var handler = registry.getHandler(PostUseCase.get(UUID.randomUUID(), UUID.randomUUID()));
    assertTrue(handler instanceof GetPostHandler);
  }

  @Test
  void getInvalid() {
    assertThrows(UnsupportedOperationException.class, () -> registry.getHandler(() -> null));
  }
}
