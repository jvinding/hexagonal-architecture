package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.usecases.AddAuthor;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public class AddAuthorHandler implements AuthorUseCaseHandler<AddAuthor, Void> {
  private final AuthorSecondaryPort secondaryPort;

  AddAuthorHandler(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public Void handle(AddAuthor useCase) throws SecondaryPortException {
    secondaryPort.add(useCase.getAuthor());
    return null;
  }
}
