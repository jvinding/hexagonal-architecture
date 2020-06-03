package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface JpaPostRepository extends CrudRepository<JpaPost, UUID> {
  List<JpaPost> findAllByAuthor(JpaAuthor author);

  Optional<JpaPost> findByAuthorAndId(JpaAuthor author, UUID id);
}
