package be.thomasmore.bookserver.controllers;

import be.thomasmore.bookserver.model.dto.SerieDetailedDTO;
import be.thomasmore.bookserver.model.dto.SeriePagedResponseDTO;
import be.thomasmore.bookserver.services.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService serieService) {
        this.serieService = serieService;
    }

    @Operation(summary = "get 1 Series from database.",
            description = "Series with id is fetched from database " +
                    "returns only id,name,description and books ")

    @GetMapping("{id:\\d+}")
    public SerieDetailedDTO findOne(@PathVariable int id) {
        log.info(String.format("##### findOne Serie %d", id));

        return serieService.findOne(id);
    }

    @Operation(summary = "get 1 Series from database.",
            description = "Series with Name is fetched from database " +
                    "returns only id,name,description and books ")
    @GetMapping("{name:.*[a-zA-Z].*}")
    public SerieDetailedDTO findSerieByName(@PathVariable String name) {
        return serieService.findByName(name);
    }

    @Operation(summary = "get all Series from database.",
            description = "Series  are fetched from database " +
                    "returns only id,name,totalElement, totalPages and currentPage ")

    @GetMapping
    public SeriePagedResponseDTO findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer books,
            @RequestParam(defaultValue = "") String sortBy)
     {
        return serieService.findAll(page, size,name,books,sortBy);
    }
}
