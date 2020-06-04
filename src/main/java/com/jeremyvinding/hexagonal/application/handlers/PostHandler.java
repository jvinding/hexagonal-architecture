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
import com.jeremyvinding.hexagonal.domain.usecases.PostUseCase;
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
  public <T extends PostUseCase<R>, R> R execute(T useCase)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    if (useCase instanceof AddPost) {
      add((AddPost) useCase);
      return null;
    } else if (useCase instanceof ListPosts) {
      return (R) list((ListPosts) useCase);
    } else if (useCase instanceof GetPost) {
      return (R) get((GetPost) useCase);
    }
    throw new UnsupportedOperationException(useCase.getClass().getSimpleName());
  }

  private void add(AddPost useCase)
      throws SecondaryPortException, MissingAuthorException, DomainException {
    var author = getAuthor(useCase.getAuthorId());
    secondaryPort.add(author, useCase.getPost());
  }

  private List<Post> list(ListPosts useCase)
      throws SecondaryPortException, DomainException, MissingAuthorException {
    var author = getAuthor(useCase.getAuthorId());
    return secondaryPort.list(author);
  }

  private Optional<Post> get(GetPost useCase)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    var author = getAuthor(useCase.getAuthorId());
    return secondaryPort.get(author, useCase.getId());
  }

  private Author getAuthor(UUID id) throws DomainException {
    return Author.of(id, null);
  }
}
