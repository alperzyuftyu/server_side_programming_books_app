package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

import java.util.List;

/**
 * Record for basic book information with authors.
 */
@Builder(toBuilder = true)
public record BookDTO(
        Integer id,
        String title,
        List<AuthorDTO> authors
) {
}
