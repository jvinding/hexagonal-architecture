package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.stereotype.Component;

@Component
class JpaAuthorSecondaryAdapter implements AuthorSecondaryPort {
  private final JpaAuthorRepository repository;

  JpaAuthorSecondaryAdapter(JpaAuthorRepository repository) {
    this.repository = repository;
  }

  @Override
  public void add(Author author) throws SecondaryPortException {
    try {
      repository.save(JpaAuthor.from(author));
    } catch (Exception e) {
      throw new SecondaryPortException("failed saving new " + author, e);
    }
  }

  @Override
  public Optional<Author> get(UUID id) throws SecondaryPortException {
    try {
      return repository.findById(id).map(JpaAuthor::toAuthor);
    } catch (Exception e) {
      throw new SecondaryPortException("failed fetching author with id " + id, e);
    }
  }

  @Override
  public List<Author> list() throws SecondaryPortException {
    try {
      return StreamSupport.stream(repository.findAll().spliterator(), false).map(JpaAuthor::toAuthor)
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new SecondaryPortException("failed listing authors", e);
    }
  }
}
