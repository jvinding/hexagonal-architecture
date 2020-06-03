package com.jeremyvinding.hexagonal.domain.model;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class Post {
  @NonNull
  UUID id;
  @NonNull
  String body;
}
