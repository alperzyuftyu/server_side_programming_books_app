package be.thomasmore.bookserver.model.converters;

import be.thomasmore.bookserver.model.Serie;
import be.thomasmore.bookserver.model.dto.SerieDetailedDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {BookDTOConverter.class})
public interface SerieDetailedDTOConverter {

    /**
     * @param serie the entity from the db
     * @return SerieDTO record to send to the client.
     */

    @Mapping(source = "books", target= "books")
    SerieDetailedDTO convertToDto(Serie serie);

    Serie convertToEntity(SerieDetailedDTO serieDto);

    Serie convertToEntity(SerieDetailedDTO serieDto, @MappingTarget Serie serie);
}
