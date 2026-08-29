package be.thomasmore.bookserver.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * Record for user data transfer.
 */
@Builder(toBuilder = true)
public record UserDTO(
        @NotBlank(message = "Username should not be blank")
        String username,
        @NotBlank(message = "Password should not be blank")
        String password,
        String email
) {
}
