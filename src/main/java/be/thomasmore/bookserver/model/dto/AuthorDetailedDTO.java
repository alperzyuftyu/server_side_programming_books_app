package be.thomasmore.bookserver.model.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Collection;

@Builder(toBuilder = true)
public record AuthorDetailedDTO(
        Integer id,
        @NotBlank(message = "Author name should not be blank")
        String name,
        Collection<BookDTO> books,
        String description,
        String country
) {


}
