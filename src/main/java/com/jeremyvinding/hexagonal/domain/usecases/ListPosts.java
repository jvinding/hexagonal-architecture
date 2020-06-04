package com.jeremyvinding.hexagonal.domain.usecases;

import com.jeremyvinding.hexagonal.domain.model.Post;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ListPosts implements PostUseCase<List<Post>> {
  @NonNull UUID authorId;
}
