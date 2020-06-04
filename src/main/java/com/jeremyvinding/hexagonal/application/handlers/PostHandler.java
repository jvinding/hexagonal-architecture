package com.jeremyvinding.hexagonal.application.handlers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.AddPost;
import com.jeremyvinding.hexagonal.domain.usecases.GetPost;
import com.jeremyvinding.hexagonal.domain.usecases.ListPosts;
import com.jeremyvinding.hexagonal.ports.primary.PostPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class PostHandler implements PostPrimaryPort {

  private final PostSecondaryPort secondaryPort;

  PostHandler(PostSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public void add(AddPost useCase)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    secondaryPort.add(getAuthor(useCase.getAuthorId()), useCase.getPost());
  }

  @Override
  public List<Post> list(ListPosts useCase)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    var author = getAuthor(useCase.getAuthorId());
    return secondaryPort.list(author);
  }

  @Override
  public Optional<Post> get(GetPost useCase)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    var author = getAuthor(useCase.getAuthorId());
    return secondaryPort.get(author, useCase.getId());
  }

  private Author getAuthor(UUID id) throws DomainException {
    return Author.of(id, null);
  }
}
