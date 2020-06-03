package com.jeremyvinding.hexagonal.adapters.primary.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.ports.primary.PostPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RestPostPrimaryAdapter {
  private final PostPrimaryPort primaryPort;

  public RestPostPrimaryAdapter(PostPrimaryPort primaryPort) {
    this.primaryPort = primaryPort;
  }

  @PutMapping("/authors/{authorId}/posts/{id}")
  @ResponseBody
  public ResponseEntity<RestPost> addPost(
      @PathVariable UUID authorId, @PathVariable UUID id, @RequestBody String body)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    var post = Post.of(id, body);
    primaryPort.add(authorId, post);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/authors/{authorId}/posts")
  @ResponseBody
  public ResponseEntity<List<RestPost>> listPosts(@PathVariable UUID authorId)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    var response =
        primaryPort.list(authorId).stream().map(RestPost::from).collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/authors/{authorId}/posts/{id}")
  @ResponseBody
  public ResponseEntity<RestPost> getPostById(@PathVariable UUID authorId, @PathVariable UUID id)
      throws DomainException, SecondaryPortException, MissingAuthorException {
    var post = primaryPort.get(authorId, id).map(RestPost::from);
    return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such author")
  @ExceptionHandler(MissingAuthorException.class)
  void noSuchAuthor() {
    // just returning 404
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(DomainException.class)
  ModelAndView domainException(DomainException e) {
    var mav = new ModelAndView();
    mav.addObject("message", e.getMessage());
    return mav;
  }
}
