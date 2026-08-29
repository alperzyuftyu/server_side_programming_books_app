package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

/**
 * Record for authentication response.
 */
@Builder(toBuilder = true)
public record AuthenticationDTO(
        String username
) {
}
