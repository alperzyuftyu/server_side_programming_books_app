package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record BookClubDTO(
        int id,
        String name
) {
}
