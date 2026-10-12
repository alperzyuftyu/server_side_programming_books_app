package be.thomasmore.bookserver.model.dto;


import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record SerieDetailedDTO(
        Integer id,
        String name,
        String description,
        List<BookDTO> books,
        Integer yearSpan
) {
    public SerieDetailedDTO{
        books = books == null ? List.of() : books;
    }

}
