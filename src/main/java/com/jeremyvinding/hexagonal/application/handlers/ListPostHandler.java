package com.jeremyvinding.hexagonal.application.handlers;

import java.util.List;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.ListPosts;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

class ListPostHandler implements PostUseCaseHandler<ListPosts, List<Post>> {

  private final PostSecondaryPort secondaryPort;

  ListPostHandler(PostSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public List<Post> handle(Author author, ListPosts useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    return secondaryPort.list(author);
  }
}
