package com.jeremyvinding.hexagonal.ports.secondary;

import com.jeremyvinding.hexagonal.domain.model.Author;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorSecondaryPort {
  void add(Author author) throws SecondaryPortException;

  List<Author> list() throws SecondaryPortException;

  Optional<Author> get(UUID id) throws SecondaryPortException;
}
