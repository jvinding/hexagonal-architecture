package com.jeremyvinding.hexagonal.domain.usecases;

import com.jeremyvinding.hexagonal.domain.model.Author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddAuthor implements AuthorUseCase<Void> {
  @NonNull
  Author author;
}
