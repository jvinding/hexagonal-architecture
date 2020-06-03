package com.jeremyvinding.hexagonal.application.handlers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.ports.primary.AuthorPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.stereotype.Component;

@Component
public class AuthorHandler implements AuthorPrimaryPort {
  private final AuthorSecondaryPort secondaryPort;

  AuthorHandler(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public void add(Author author) throws DomainException, SecondaryPortException {
    secondaryPort.add(author);
  }

  @Override
  public List<Author> list() throws DomainException, SecondaryPortException {
    return secondaryPort.list();
  }

  @Override
  public Optional<Author> get(UUID authorId) throws DomainException, SecondaryPortException {
    return secondaryPort.get(authorId);
  }
}
