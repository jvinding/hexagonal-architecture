package com.jeremyvinding.hexagonal.adapters.primary.rest;

import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.model.Author;

import lombok.Value;

@Value
class RestAuthor {
  UUID id;
  String name;

  static RestAuthor from(Author author) {
    return new RestAuthor(author.getId(), author.getName());
  }
}
