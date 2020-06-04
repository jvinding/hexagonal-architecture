package com.jeremyvinding.hexagonal.domain.usecases;

import com.jeremyvinding.hexagonal.domain.model.Author;
import java.util.UUID;

public interface AuthorUseCase<ResponseType> {

  static AddAuthor add(Author author) {
    return new AddAuthor(author);
  }

  static ListAuthors list() {
    return new ListAuthors();
  }

  static GetAuthor get(UUID id) {
    return new GetAuthor(id);
  }
}
