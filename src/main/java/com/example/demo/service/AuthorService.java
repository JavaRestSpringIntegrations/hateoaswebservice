package com.example.demo.service;

import com.example.demo.model.Author;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class AuthorService {

    private static List<Author> authors = new ArrayList<>();
    private int authorIdUpdate = 103;

    static {
        authors.add(new Author(101,"Michael","Criton"));
        authors.add(new Author(102,"Robin","Cook"));
        authors.add(new Author(103,"Chetan","Bhagat"));

    }

    public List<Author> retrieveAllAuthor() {
        return authors;
    }

    public Author retrieveSingleUser(int id) {
        for (Author author:authors) {
            if (author.getAuthorId() == id) {
                return author;
            }
        }

        return null;
    }

    public String deleteSingleAuthor(int id) {
        Iterator<Author> iterator = authors.iterator();
        while (iterator.hasNext()) {
            Author author = iterator.next();
            if(author.getAuthorId() == id) {
                iterator.remove();
                return "Removed "+ id;
            }
        }

        return null;
    }

    public Author addAuthor(Author author) {
        if (author.getAuthorId() == null) {
            author.setAuthorId(++authorIdUpdate);
        }
        authors.add(author);
        return author;
    }
}
