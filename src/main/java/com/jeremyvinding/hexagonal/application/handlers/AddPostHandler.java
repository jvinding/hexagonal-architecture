package com.jeremyvinding.hexagonal.application.handlers;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AddPost;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public class AddPostHandler implements PostUseCaseHandler<AddPost, Void> {

  private final PostSecondaryPort secondaryPort;

  AddPostHandler(PostSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public Void handle(Author author, AddPost useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    secondaryPort.add(author, useCase.getPost());
    return null;
  }
}
