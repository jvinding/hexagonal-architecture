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

import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
import com.jeremyvinding.hexagonal.ports.primary.PostPrimaryPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

class RestPostPrimaryAdapterTest {
  @Mock
  PostPrimaryPort primaryPort;
  RestPostPrimaryAdapter adapter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    adapter = new RestPostPrimaryAdapter(primaryPort);
  }

  private void assertPostEquals(Post post, RestPost restPost) {
    assertNotNull(post);
    assertNotNull(restPost);
    assertEquals(post.getId(), restPost.getId());
    assertEquals(post.getBody(), restPost.getBody());
  }

  @Nested
  class ListPosts {
    @Test
    void success() throws Exception {
      var authorId = UUID.randomUUID();
      var post = Post.of(UUID.randomUUID(), "post");
      doReturn(List.of(post)).when(primaryPort).list(PostUseCase.list(authorId));

      var response = adapter.listPosts(authorId);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(1, response.getBody().size());
      assertPostEquals(post, response.getBody().get(0));
    }

    @Test
    void noResults() throws Exception {
      var authorId = UUID.randomUUID();
      doReturn(List.of()).when(primaryPort).list(eq(PostUseCase.list(authorId)));

      var response = adapter.listPosts(authorId);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(0, response.getBody().size());
    }
  }

  @Nested
  class GetPostById {
    @Test
    void success() throws Exception {
      var authorId = UUID.randomUUID();
      var postId = UUID.randomUUID();
      var post = Post.of(postId, "Post");
      doReturn(Optional.of(post)).when(primaryPort).get(eq(PostUseCase.get(authorId, postId)));

      var response = adapter.getPostById(authorId, postId);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertPostEquals(post, response.getBody());
    }

    @Test
    void noResults() throws Exception {
      doReturn(Optional.empty()).when(primaryPort).get(any());

      var response = adapter.getPostById(UUID.randomUUID(), UUID.randomUUID());

      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
  }

  @Nested
  class AddPost {
    @Test
    void success() throws Exception {
      var post = Post.of(UUID.randomUUID(), "Post");
      var authorId = UUID.randomUUID();

      var response = adapter.addPost(authorId, post.getId(), post.getBody());

      verify(primaryPort, times(1)).add(eq(PostUseCase.add(authorId, post)));

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
  }
}
