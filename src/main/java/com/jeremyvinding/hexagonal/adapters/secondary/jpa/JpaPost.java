package com.jeremyvinding.hexagonal.adapters.secondary.jpa;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.jeremyvinding.hexagonal.domain.model.Post;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "post")
class JpaPost {
  @Id
  UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  JpaAuthor author;

  @Column
  String body;

  static JpaPost from(@NonNull JpaAuthor author, @NonNull Post post) {
    var output = new JpaPost();
    output.author = author;
    output.id = post.getId();
    output.body = post.getBody();
    return output;
  }

  Post toPost() {
    return Post.of(id, body);
  }
}
