package com.jeremyvinding.hexagonal.application.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

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

class GetPostHandlerTest {
  @Mock
  PostSecondaryPort secondaryPort;
  private GetPostHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    handler = new GetPostHandler(secondaryPort);
  }

  @Test
  void getPost() throws Exception {
    var author = Author.of(UUID.randomUUID(), null);
    var id = UUID.randomUUID();
    var post = Post.of(id, "Post");
    var useCase = PostUseCase.get(author.getId(), id);
    doReturn(Optional.of(post)).when(secondaryPort).get(eq(author), eq(id));

    var resultOptional = handler.handle(author, useCase);

    assertTrue(resultOptional.isPresent());
    assertEquals(post, resultOptional.get());
  }
}
