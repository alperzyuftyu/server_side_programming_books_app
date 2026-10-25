package be.thomasmore.bookserver.model.converters;


import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.dto.AuthorDetailedDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookDTOConverter.class})
public interface AuthorDetailedDTOConverter {
    /**
     * Convert entity to DTO.
     *
     * @param author the entity from the db
     * @return AuthorDetailedDTO record
     */
    AuthorDetailedDTO convertToDto(Author author);

    /**
     * Convert DTO to new entity for creation.
     *
     * @param authorDto the data from client
     * @return new Author entity ready to save
     */
    Author convertToEntity(AuthorDetailedDTO authorDto);


}
