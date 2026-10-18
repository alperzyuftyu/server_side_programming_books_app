package be.thomasmore.bookserver.model.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record MemberDTO(
        Integer id,
        String username
) {
}
