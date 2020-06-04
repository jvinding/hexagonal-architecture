package com.jeremyvinding.hexagonal.domain.usecases;

import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GetAuthor implements AuthorUseCase<Optional<Author>> {
  @NonNull
  UUID id;
}
