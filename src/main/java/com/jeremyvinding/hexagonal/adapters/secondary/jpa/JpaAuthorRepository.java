package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface JpaAuthorRepository extends CrudRepository<JpaAuthor, UUID> {
}
