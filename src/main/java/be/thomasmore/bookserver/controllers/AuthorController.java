package be.thomasmore.bookserver.controllers;


import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.model.dto.AuthorDetailedDTO;
import be.thomasmore.bookserver.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@Slf4j

public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @Operation(summary = "list of authors in the database.",
            description = "temporary there is no keyword search function")


    @GetMapping("")
    public Iterable<AuthorDTO> findAllAuthors() {
        return authorService.findAll();
    }

    @Operation(summary = "get 1 author from database.",
            description = "author with id is fetched from database " +
                    "returns only id,name and collection of books ")

    @GetMapping("/{id:\\d+}")
    public AuthorDetailedDTO findAuthorById(@PathVariable int id) {
        return authorService.findById(id);
    }

    @Operation(summary = "get 1 author from database.",
            description = "author with name is fetched from database" +
                    "returns only id,name and collection of books ")


    @GetMapping("{name:.*[a-zA-Z].*}")
    public AuthorDetailedDTO findAuthorByName(@PathVariable String name) {
        return authorService.findByName(name);
    }





    @Operation(summary = "create a new author in the database.",
            description = "The name of the author can be dublicated.</br>" +
                    "The books are <b>not</b> updated in the new author.</br>" +
                    "Use PUT api/authors/{id}/books to update those. </br>" +
                    "</br>" +
                    "Returns new authoryyy (containing id from database). ")
    @PostMapping("")
    public AuthorDetailedDTO create(@Valid @RequestBody AuthorDetailedDTO authorDto) {
        log.info("##### create author");
        return authorService.create(authorDto);
    }



    @Operation(summary = "edit existing author in the database.",
            description = "The books are <b>not</b> updated in the new author.</br>" +
                    "Use PUT api/author/{id}/books to update those. </br>" +
                    "</br>" +
                    "Returns updated author. ")
    @PutMapping("/{id}")
    public AuthorDetailedDTO edit(
            @PathVariable int id,
            @Valid @RequestBody AuthorDetailedDTO authorDto
    ) {
        return authorService.edit(id, authorDto);
    }
}
