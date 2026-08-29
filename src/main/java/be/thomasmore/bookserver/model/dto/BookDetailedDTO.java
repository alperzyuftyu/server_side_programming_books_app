package be.thomasmore.bookserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

/**
 * Detailed book response DTO (JDK 16+ record).
 */
@Builder(toBuilder = true)
public record BookDetailedDTO(
        Integer id,
        @NotBlank(message = "Book Title should not be blank")
        String title,
        List<AuthorDTO> authors
) {
    /**
     * Compact constructor for validation/normalization.
     */
    public BookDetailedDTO {
        authors = authors == null ? List.of() : authors;
    }
}
