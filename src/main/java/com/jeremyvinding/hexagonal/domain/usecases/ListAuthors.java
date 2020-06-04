package com.jeremyvinding.hexagonal.domain.usecases;

import com.jeremyvinding.hexagonal.domain.model.Author;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ListAuthors implements AuthorUseCase<List<Author>> {}
