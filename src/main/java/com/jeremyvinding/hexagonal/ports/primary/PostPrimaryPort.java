package com.jeremyvinding.hexagonal.ports.primary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface PostPrimaryPort {
    void add(UUID authorId, Post post) throws MissingAuthorException, DomainException, SecondaryPortException;

    List<Post> list(UUID authorId) throws MissingAuthorException, DomainException, SecondaryPortException;

    Optional<Post> get(UUID authorId, UUID id) throws MissingAuthorException, DomainException, SecondaryPortException;
}
