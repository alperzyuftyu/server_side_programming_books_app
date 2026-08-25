package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

/**
 * Record for basic author information.
 * JDK 16+ feature - immutable data carrier with automatic equals(), hashCode(), toString().
 */
@Builder(toBuilder = true)
public record AuthorDTO(
        Integer id,
        String name
) {
}
