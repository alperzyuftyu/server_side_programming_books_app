package be.thomasmore.bookserver.controllers.series;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.model.Serie;
import be.thomasmore.bookserver.model.dto.SerieDetailedDTO;
import be.thomasmore.bookserver.repositories.SerieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SerieControllerCreateTest extends AbstractIntegrationTest {
    @Autowired
    private SerieRepository serieRepository;

    @Test
    @WithMockUser
    public void createSerie() throws Exception {

        SerieDetailedDTO newSerieDto = SerieDetailedDTO.builder()
                .name("Harry Potter")
                .build();

        mockMvc.perform(
                        getMockRequestPost("/api/series", newSerieDto)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry Potter"));

        Serie savedSerie = serieRepository
                .findByName("Harry Potter")
                .orElseThrow();

        assertThat(savedSerie.getName())
                .isEqualTo("Harry Potter");
    }

    @Test
    @WithMockUser
    public void createSerie_nameCannotBeBlank() throws Exception {

        SerieDetailedDTO newSerieDto = SerieDetailedDTO.builder()
                .name("")
                .build();

        mockMvc.perform(
                        getMockRequestPost("/api/series", newSerieDto)
                )
                .andExpect(status().isBadRequest());

        assertThat(serieRepository.count()).isEqualTo(0);
    }
}
