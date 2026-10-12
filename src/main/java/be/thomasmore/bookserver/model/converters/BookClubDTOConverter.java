package be.thomasmore.bookserver.model.converters;

import be.thomasmore.bookserver.model.BookClub;
import be.thomasmore.bookserver.model.dto.BookClubDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookClubDTOConverter {

    /**
     * @param bookClub the entity from the db
     * @return BookClubDTO record to send to the client.
     */

    BookClubDTO convertToDto(BookClub bookClub);
}
