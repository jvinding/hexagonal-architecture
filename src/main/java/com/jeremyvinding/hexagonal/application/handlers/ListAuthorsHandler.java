package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.ListAuthors;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import java.util.List;

public class ListAuthorsHandler implements AuthorUseCaseHandler<ListAuthors, List<Author>> {
  private final AuthorSecondaryPort secondaryPort;

  ListAuthorsHandler(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public List<Author> handle(ListAuthors useCase) throws DomainException, SecondaryPortException {
    return secondaryPort.list();
  }
}
