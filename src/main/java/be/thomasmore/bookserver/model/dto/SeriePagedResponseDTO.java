package be.thomasmore.bookserver.model.dto;


import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record SeriePagedResponseDTO(
       List<SerieDTO> content,
       long totalElements,
       int totalPages,
       int currentPage
) {
}
