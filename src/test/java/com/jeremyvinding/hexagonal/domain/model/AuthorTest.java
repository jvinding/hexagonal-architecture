package com.jeremyvinding.hexagonal.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;

import org.junit.jupiter.api.Test;

class AuthorTest {
  @Test
  void validAuthor() throws Exception {
    var id = UUID.randomUUID();
    var name = "Name";
    var author = Author.of(id, name);

    assertEquals(id, author.getId());
    assertEquals(name, author.getName());
  }

  @Test
  void invalidAuthor() throws Exception {
    var id = UUID.randomUUID();
    var name = "Bad Name";
    assertThrows(DomainException.class, () -> Author.of(id, name));
  }
}
