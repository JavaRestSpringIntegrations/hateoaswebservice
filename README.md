# Web services and HATEOAS

HATEOAS ( Hyper Media As The Engine Of Application State ) is used to present information about a REST API to a client. This is done by including links in a returned response and using only these links to further communicate with the server.

This reduces the likelihood of the client breaking due to changes to the service ( not cent percent safe). Add with Maven/Gradle dependency. Maven dependency is shown below:

```xml
<dependency>  
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-hateoas</artifactId>
</dependency>
```

A basic rest service is shown below (without HATEOAS):

```java
@GetMapping("/authors/{id}")
    public Author getAuthorByAuthorId(@PathVariable int id) {
        return authorService.retrieveSingleUser(id);
    }    
```

Each endpoint returns a `ResponseEntity` with most of them (not `DELETE`) containing a `Author` / `Resources<Author>`. But HATEOAS service normally returns the `ResponseEntity` object directly.   In this scenario, it returns `ResponseEntity<AuthorResource>` (HATEOAS) instead of `ResponseEntity<Author>` (REST). Response using HATEOAS service is as below:

```json
{
    "authorId": 101,
    "firstName": "Michael",
    "lastName": "Criton",
    "_links": {
        "self": {
            "href": "http://localhost:8080/authors/101"
        }
    }
}
```



The code for the above response is shown as below:

```java
@GetMapping("/authors/{id}")
    public Resource<Author> getAuthorByAuthorId(@PathVariable int id) {
        Author singleAuthor = authorService.retrieveSingleUser(id);
        if (singleAuthor == null) {
            throw new AuthorNotFoundEception("Post ID: "+id);
        }

        Resource<Author> resource = new Resource<>(singleAuthor);
        Link selfRelLink = ControllerLinkBuilder.linkTo(
                ControllerLinkBuilder.methodOn(AuthorController.class)
                .getAuthorByAuthorId(id))
                .withSelfRel();

        resource.add(selfRelLink);
        return resource;
    }
```

Let's break down the parts:

1. add - is a method inherited from `ResourceSupport` , which adds the link passed to it
2. linkTo - creates the link. Also, inspects the controller class and obtains its root mapping
3. methodOn - gets the URI for `AuthorController.getAuthorById` method 
4. The `id` has been passed to `getAuthorById` method, allowing the `{id}` in the URI to be replaced by input value
5. Once the link is created, `withSelfRel` is called to provide a name to describe how it is related to the resource. `withSelfRel` simply names the relation as self-link
6. ControllerLinkBuilder - Used to simplify building URI's by avoiding hard-coded links

