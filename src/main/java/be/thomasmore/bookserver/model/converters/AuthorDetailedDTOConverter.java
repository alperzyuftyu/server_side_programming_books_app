package be.thomasmore.bookserver.model.converters;


import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.dto.AuthorDetailedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

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



    /**
     * Update existing entity with DTO data.
     * Note: books relationship is NOT touched.
     *
     * @param authorDto the data from client
     * @param author    the original book entity (from db)
     */
    Author convertToEntity(
            AuthorDetailedDTO authorDto,
            @MappingTarget Author author
    );


}
