package com.jeremyvinding.hexagonal.domain.usecases;

import com.jeremyvinding.hexagonal.domain.model.Post;
import java.util.UUID;

public interface PostUseCase<ResponseType> {

  static AddPost add(UUID authorId, Post post) {
    return new AddPost(authorId, post);
  }

  static ListPosts list(UUID authorId) {
    return new ListPosts(authorId);
  }

  static GetPost get(UUID authorId, UUID postId) {
    return new GetPost(authorId, postId);
  }
}
