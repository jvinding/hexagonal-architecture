package com.jeremyvinding.hexagonal.domain.usecases;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddPost implements PostUseCase<Void> {
  @NonNull
  UUID authorId;
  @NonNull
  Post post;
}
