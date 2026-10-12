package be.thomasmore.bookserver.model.converters;


import be.thomasmore.bookserver.model.Serie;
import be.thomasmore.bookserver.model.dto.SerieDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BookDTOConverter.class})
public interface SerieDTOConverter {


    /**
     * @param serie the entity from the db
     * @return SerieDTO record to send to the client.
     */

    SerieDTO convertToDTO(Serie serie);

}
