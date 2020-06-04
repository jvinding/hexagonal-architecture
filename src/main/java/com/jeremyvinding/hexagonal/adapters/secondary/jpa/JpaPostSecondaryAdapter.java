package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.PostSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.stereotype.Component;

@Component
class JpaPostSecondaryAdapter implements PostSecondaryPort {
  private final JpaAuthorRepository authorRepository;
  private final JpaPostRepository postRepository;

  JpaPostSecondaryAdapter(JpaAuthorRepository authorRepository, JpaPostRepository postRepository) {
    this.authorRepository = authorRepository;
    this.postRepository = postRepository;
  }

  @Override
  public void add(Author author, Post post) throws SecondaryPortException, MissingAuthorException {
    try {
      var jpaAuthor = getJpaAuthor(author, "add");
      postRepository.save(JpaPost.from(jpaAuthor, post));
    } catch (MissingAuthorException e) {
      throw e;
    } catch (Exception e) {
      throw new SecondaryPortException("Failed adding " + post + " to " + author, e);
    }
  }

  @Override
  public List<Post> list(Author author) throws SecondaryPortException, MissingAuthorException {
    try {
      var jpaAuthor = getJpaAuthor(author, "list");
      return postRepository.findAllByAuthor(jpaAuthor).stream().map(JpaPost::toPost).collect(Collectors.toList());
    } catch (MissingAuthorException e) {
      throw e;
    } catch (Exception e) {
      throw new SecondaryPortException("Failed listing posts for " + author, e);
    }
  }

  @Override
  public Optional<Post> get(Author author, UUID id) throws SecondaryPortException, MissingAuthorException {
    try {
      var jpaAuthor = getJpaAuthor(author, "get");
      Optional<JpaPost> jpaPost = postRepository.findByAuthorAndId(jpaAuthor, id);
      return jpaPost.map(JpaPost::toPost);
    } catch (MissingAuthorException e) {
      throw e;
    } catch (Exception e) {
      throw new SecondaryPortException("Failed finding " + id + " on " + author, e);
    }
  }

  private JpaAuthor getJpaAuthor(Author author, String operation) throws MissingAuthorException {
    return authorRepository.findById(author.getId())
        .orElseThrow(() -> new MissingAuthorException(author.getId(), "Failed to " + operation + " post"));
  }
}
