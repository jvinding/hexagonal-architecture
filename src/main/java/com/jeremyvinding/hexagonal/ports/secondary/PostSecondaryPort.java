package com.jeremyvinding.hexagonal.ports.secondary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;

public interface PostSecondaryPort {
  void add(Author author, Post post) throws SecondaryPortException, MissingAuthorException;

  List<Post> list(Author author) throws SecondaryPortException, MissingAuthorException;

  Optional<Post> get(Author author, UUID id) throws SecondaryPortException, MissingAuthorException;
}
