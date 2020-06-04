package com.jeremyvinding.hexagonal.application.handlers;

import java.util.List;
import java.util.Optional;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;
import com.jeremyvinding.hexagonal.domain.usecases.AddAuthor;
import com.jeremyvinding.hexagonal.domain.usecases.AuthorUseCase;
import com.jeremyvinding.hexagonal.domain.usecases.GetAuthor;
import com.jeremyvinding.hexagonal.domain.usecases.ListAuthors;
import com.jeremyvinding.hexagonal.ports.primary.AuthorPrimaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.AuthorSecondaryPort;
import com.jeremyvinding.hexagonal.ports.secondary.SecondaryPortException;

import org.springframework.stereotype.Component;

@Component
public class AuthorHandler implements AuthorPrimaryPort {
  private final AuthorSecondaryPort secondaryPort;

  AuthorHandler(AuthorSecondaryPort secondaryPort) {
    this.secondaryPort = secondaryPort;
  }

  @Override
  public <T extends AuthorUseCase<R>, R> R execute(T useCase) throws DomainException, SecondaryPortException {
    if (useCase instanceof AddAuthor) {
      add((AddAuthor) useCase);
      return null;
    } else if (useCase instanceof ListAuthors) {
      return (R) list();
    } else if (useCase instanceof GetAuthor) {
      return (R) get((GetAuthor) useCase);
    }
    throw new UnsupportedOperationException(useCase.getClass().getSimpleName());
  }

  private void add(AddAuthor useCase) throws SecondaryPortException {
    secondaryPort.add(useCase.getAuthor());
  }

  private List<Author> list() throws SecondaryPortException {
    return secondaryPort.list();
  }

  private Optional<Author> get(GetAuthor command) throws SecondaryPortException {
    return secondaryPort.get(command.getId());
  }
}
