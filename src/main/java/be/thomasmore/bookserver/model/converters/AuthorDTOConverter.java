package be.thomasmore.bookserver.model.converters;

import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.dto.AuthorDTO;
import org.mapstruct.Mapper;

/**
 * Converter for Author entity to AuthorDTO record using MapStruct.
 */
@Mapper(componentModel = "spring")
public interface AuthorDTOConverter {

    /**
     * @param author the entity from the db
     * @return AuthorDTO record to send to the client.
     */
    AuthorDTO convertToDto(Author author);

    default Author toEntity(AuthorDTO authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new Author();
    }
}
