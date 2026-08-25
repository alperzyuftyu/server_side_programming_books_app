package be.thomasmore.bookserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import java.util.List;

/**
 * Detailed book response DTO (JDK 16+ record).
 * Immutable data carrier - all fields set at construction.
 * booksSameAuthor is computed and passed at construction time.
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
        // Ensure lists are never null for consistent API responses
        authors = authors == null ? List.of() : authors;
    }
}
