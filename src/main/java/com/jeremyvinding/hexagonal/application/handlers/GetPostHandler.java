package com.jeremyvinding.hexagonal.application.handlers;

import java.util.Optional;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.GetPost;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

class GetPostHandler implements PostUseCaseHandler<GetPost, Optional<Post>> {

  private final PostSecondaryPort secondaryPort;

  GetPostHandler(PostSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public Optional<Post> handle(Author author, GetPost useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    return secondaryPort.get(author, useCase.getId());
  }
}
