package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

import java.util.Set;

@Builder(toBuilder = true)
public record MemberDetailedDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Set<BookDTO> favoriteBooks
) {
}
