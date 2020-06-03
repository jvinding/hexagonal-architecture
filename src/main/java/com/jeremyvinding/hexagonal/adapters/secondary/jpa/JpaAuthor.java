package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.jeremyvinding.hexagonal.domain.exceptions.DomainException;
import com.jeremyvinding.hexagonal.domain.model.Author;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "author")
class JpaAuthor {
  @Id
  private UUID id;
  @Column
  private String name;

  @OneToMany(mappedBy = "author")
  private List<JpaPost> posts;

  static JpaAuthor from(Author author) {
    var output = new JpaAuthor();
    output.id = author.getId();
    output.name = author.getName();
    return output;
  }

  Author toAuthor() {
    try {
      return Author.of(id, name);
    } catch (DomainException e) {
      throw new IllegalStateException("Illegal Author found: " + id, e);
    }
  }
}
