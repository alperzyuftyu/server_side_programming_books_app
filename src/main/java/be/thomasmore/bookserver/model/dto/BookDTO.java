package be.thomasmore.bookserver.model.dto;

import lombok.Builder;
import java.util.List;

/**
 * Record for basic book information with authors.
 * JDK 16+ feature - immutable data carrier.
 * Uses List instead of Collection for better API clarity (JDK 21+ sequenced collections).
 */
@Builder(toBuilder = true)
public record BookDTO(
        Integer id,
        String title,
        List<AuthorDTO> authors
) {
}
