package com.jeremyvinding.hexagonal.adapters.primary.rest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.ports.primary.AuthorPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RestAuthorPrimaryAdapter {
  private final AuthorPrimaryPort primaryPort;

  RestAuthorPrimaryAdapter(AuthorPrimaryPort primaryPort) {
    this.primaryPort = primaryPort;
  }

  @PutMapping("/authors/{id}")
  @ResponseBody
  public ResponseEntity<Void> addAuthor(@PathVariable("id") UUID id, @RequestBody String name)
      throws SecondaryPortException {
    try {
      primaryPort.add(AuthorUseCase.add(Author.of(id, name)));
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (DomainException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }
  }

  @GetMapping("/authors")
  @ResponseBody
  public List<RestAuthor> listAuthors() throws DomainException, SecondaryPortException {
    return primaryPort.list().stream().map(RestAuthor::from).collect(Collectors.toList());
  }

  @GetMapping("/authors/{id}")
  @ResponseBody
  public ResponseEntity<RestAuthor> getAuthor(@PathVariable UUID id) throws DomainException, SecondaryPortException {
    var author = primaryPort.get(AuthorUseCase.get(id)).map(RestAuthor::from);
    return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
