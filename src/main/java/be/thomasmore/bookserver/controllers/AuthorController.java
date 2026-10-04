package be.thomasmore.bookserver.controllers;


import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
