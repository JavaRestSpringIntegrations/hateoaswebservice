package com.example.demo.controller;

import com.example.demo.exception.AuthorNotFoundEception;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public Resources<Author> getAllAuthors() {
        List<Author> allAuthors = authorService.retrieveAllAuthor();

//        for (Author author:allAuthors) {
//            Integer authorId = author.getAuthorId();
//            Link selfLink = ControllerLinkBuilder
//                    .linkTo(AuthorController.class)
//                    .slash(authorId)
//                    .withSelfRel();
//          }

            Link selfRelLink = ControllerLinkBuilder.linkTo(
                    ControllerLinkBuilder.methodOn(AuthorController.class)
                            .getAllAuthors())
                            .withSelfRel();

        Resources<Author> resources = new Resources<>(allAuthors);
        resources.add(selfRelLink);
        return resources;
    }

    @GetMapping("/authors/{id}")
    public Resource<Author> getAuthorByAuthorId(@PathVariable int id) {
        Author singleAuthor = authorService.retrieveSingleUser(id);
        if (singleAuthor == null) {
            throw new AuthorNotFoundEception("Post ID: "+id);
        }

        Resource<Author> resource = new Resource<>(singleAuthor);
        Link selfRelLink = linkTo(
                ControllerLinkBuilder.methodOn(AuthorController.class)
                .getAuthorByAuthorId(id))
                .withSelfRel();

        resource.add(selfRelLink);
        return resource;
    }

    @PostMapping("/authors")
    public Author createAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable int id) {
        String author = authorService.deleteSingleAuthor(id);

        if (author == null) {
            return new ResponseEntity<>("Id Not found -"+id, HttpStatus.NOT_FOUND);
        }

        return null;
    }
}
