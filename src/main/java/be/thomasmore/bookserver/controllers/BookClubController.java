package be.thomasmore.bookserver.controllers;


import be.thomasmore.bookserver.model.BookClub;
import be.thomasmore.bookserver.model.dto.BookClubDTO;
import be.thomasmore.bookserver.services.BookClubService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookclubs")
@Slf4j
public class BookClubController {

    private final BookClubService bookClubService;

    public BookClubController(BookClubService bookClubService) {
        this.bookClubService = bookClubService;
    }

    @Operation(summary = "list of all book clubs in the database.",
            description = "retrives all book clubs from database" )

    @GetMapping("")
    public List<BookClubDTO> findAll() {
        return bookClubService.findAll();
    }

    @Operation(summary = "search book clubs in the database.",
            description = "If Request Parameter <b>filter</b> is given: " +
                    "only book clubs where the name , meeting place, city or language contains this filter (ignore-case). </br>" +
                    "Otherwise all book clubs are returned. </br>" +
                    "</br>" +
                    "If Request Parameter <b>language</b> is given: " +
                    "only book clubs where the language is same as language keyword")

    @GetMapping("/search")
    public List<BookClub> searchBookClubs(@RequestParam(required = false) String filter,
                                          @RequestParam(required = false) String language){
      return bookClubService.searchBookClubs(filter,language);
    }
}
