package com.jeremyvinding.hexagonal.application.handlers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
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
  public void add(UUID authorId, Post post)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    secondaryPort.add(getAuthor(authorId), post);
  }

  @Override
  public List<Post> list(UUID authorId)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    var author = getAuthor(authorId);
    return secondaryPort.list(author);
  }

  @Override
  public Optional<Post> get(UUID authorId, UUID id)
      throws MissingAuthorException, DomainException, SecondaryPortException {
    var author = getAuthor(authorId);
    return secondaryPort.get(author, id);
  }

  private Author getAuthor(UUID id) throws DomainException {
    return Author.of(id, null);
  }
}
