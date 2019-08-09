package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;


@Getter @Setter
@NoArgsConstructor
public class Author extends ResourceSupport {
    private Integer authorId;
    private String firstName;
    private String lastName;

    public Author(Integer authorId, String firstName, String lastName) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
