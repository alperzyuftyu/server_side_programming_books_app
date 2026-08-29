package be.thomasmore.bookserver.controllers;

import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.model.dto.BookDTO;
import be.thomasmore.bookserver.model.dto.BookDetailedDTO;
import be.thomasmore.bookserver.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for book operations.
 */
@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "list of books in the database.",
            description = "If Request Parameter <b>titleKeyWord</b> is given: " +
                    "only books where the title contains this titleKeyWord (ignore-case). </br>" +
                    "Otherwise all books are returned. </br>" +
                    "</br>" +
                    "The authors Collection contains only id and name. </br>" +
                    "Use GET api/authors/{id}/authors  to fetch more info about the authors. ")
    @GetMapping("")
    public Iterable<BookDTO> findAll(@RequestParam(required = false) String titleKeyWord) {
        log.info("##### findAll books - titleKeyWord=" + titleKeyWord);
        return bookService.findAll(titleKeyWord);
    }

    @Operation(summary = "get 1 book from the database.",
            description = "Book with id is fetched from database - returns detailed info. " +
                    "</br>" +
                    "The authors Collection contains only id and name. </br>" +
                    "Use GET api/authors/{id} to fetch more info about the authors. ")
    @GetMapping("{id}")
    public BookDetailedDTO findOne(@PathVariable int id) {
        log.info(String.format("##### findOne book %d", id));
        return bookService.findOne(id);
    }

    @Operation(summary = "create a new book in the database.",
            description = "The title of the book must be unique.</br>" +
                    "The authors are <b>not</b> updated in the new book.</br>" +
                    "Use PUT api/books/{id}/authors to update those. </br>" +
                    "</br>" +
                    "Returns new book (containing id from database). ")
    @PostMapping("")
    public BookDetailedDTO create(@Valid @RequestBody BookDetailedDTO bookDto) {
        log.info("##### create book");
        return bookService.create(bookDto);
    }

    @Operation(summary = "edit existing book in the database.",
            description = "The authors are <b>not</b> updated in the new book.</br>" +
                    "Use PUT api/books/{id}/authors to update those. </br>" +
                    "</br>" +
                    "Returns updated book. ")
    @PutMapping("{id}")
    public BookDetailedDTO edit(@PathVariable int id, @Valid @RequestBody BookDetailedDTO bookDto) {
        log.info(String.format("##### edit book %d", id));
        return bookService.edit(id, bookDto);
    }

    @Operation(summary = "find the authors for the given book. ",
            description = "Returns authors collection that contains only id and name. </br>" +
                    "Use GET api/authors/{id}/authors to fetch more info about the authors. ")
    @GetMapping("{id}/authors")
    public Iterable<AuthorDTO> authorsForBook(@PathVariable int id) {
        log.info(String.format("##### get authors for book with id %d", id));
        return bookService.authorsForBook(id);
    }

    @Operation(summary = "delete a book from the database.",
            description = "Book with the given id is removed from the database. </br>" +
                    "If the book does not exist, a 404 NOT FOUND status is returned. </br>" +
                    "When successful, this request returns a 204 NO_CONTENT.")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info(String.format("##### delete book %d", id));
        bookService.delete(id);
    }

}
