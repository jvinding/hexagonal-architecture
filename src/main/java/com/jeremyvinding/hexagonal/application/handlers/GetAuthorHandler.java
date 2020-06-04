package com.jeremyvinding.hexagonal.application.handlers;

import java.util.Optional;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.GetAuthor;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public class GetAuthorHandler implements AuthorUseCaseHandler<GetAuthor, Optional<Author>> {
  private final AuthorSecondaryPort secondaryPort;

  GetAuthorHandler(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public Optional<Author> handle(GetAuthor useCase) throws DomainException, SecondaryPortException {
    return secondaryPort.get(useCase.getId());
  }
}
