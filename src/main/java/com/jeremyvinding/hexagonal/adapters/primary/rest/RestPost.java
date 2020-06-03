package com.jeremyvinding.hexagonal.adapters.primary.rest;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Post;

import lombok.NonNull;
import lombok.Value;

@Value
class RestPost {
  @NonNull
  UUID id;
  @NonNull
  String body;

  static RestPost from(Post post) {
    return new RestPost(post.getId(), post.getBody());
  }
}
