package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SerieDTO(
        Integer id,
        String name
) {
}
