package com.jeremyvinding.hexagonal.ports.primary;

import java.util.List;
import java.util.Optional;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Post;
import com.jeremyvinding.hexagonal.domain.usecases.AddPost;
import com.jeremyvinding.hexagonal.domain.usecases.GetPost;
import com.jeremyvinding.hexagonal.domain.usecases.ListPosts;
import com.jeremyvinding.hexagonal.ports.secondary.MissingAuthorException;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

public interface PostPrimaryPort {
    void add(AddPost useCase) throws MissingAuthorException, DomainException, SecondaryPortException;

    List<Post> list(ListPosts useCase) throws MissingAuthorException, DomainException, SecondaryPortException;

    Optional<Post> get(GetPost useCase) throws MissingAuthorException, DomainException, SecondaryPortException;
}
