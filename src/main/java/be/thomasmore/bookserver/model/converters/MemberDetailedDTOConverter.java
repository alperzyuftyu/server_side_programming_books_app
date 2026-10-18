package be.thomasmore.bookserver.model.converters;


import be.thomasmore.bookserver.model.Member;
import be.thomasmore.bookserver.model.dto.MemberDetailedDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookDTOConverter.class})
public interface MemberDetailedDTOConverter {
MemberDetailedDTO convertToDTO(Member member);
}
