package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

/**
 * Record for basic author information.
 */
@Builder(toBuilder = true)
public record AuthorDTO(
        Integer id,
        String name
) {
}
