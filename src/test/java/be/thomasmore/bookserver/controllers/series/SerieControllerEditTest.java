package be.thomasmore.bookserver.controllers.series;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.model.Serie;
import be.thomasmore.bookserver.model.dto.SerieDetailedDTO;
import be.thomasmore.bookserver.repositories.SerieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/series/create_empty_serie.sql")
@Sql(
        scripts = "/sql/series/clean_series.sql",
        executionPhase = AFTER_TEST_METHOD
)

public class SerieControllerEditTest extends AbstractIntegrationTest {
    @Autowired
    private SerieRepository serieRepository;

    @Test
    @WithMockUser
    public void editSerie() throws Exception {

        SerieDetailedDTO updatedSerieDto = SerieDetailedDTO.builder()
                .id(1)
                .name("New Serie Name")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/series/1", updatedSerieDto)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Serie Name"));

        Serie updatedSerie = serieRepository.findById(1)
                .orElseThrow();

        assertThat(updatedSerie.getName())
                .isEqualTo("New Serie Name");
    }

    @Test
    @WithMockUser
    public void editSerie_notFound() throws Exception {

        SerieDetailedDTO updatedSerieDto = SerieDetailedDTO.builder()
                .id(999)
                .name("New Serie Name")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/series/999", updatedSerieDto)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void editSerie_idMustMatchUrl() throws Exception {

        SerieDetailedDTO updatedSerieDto = SerieDetailedDTO.builder()
                .id(2)
                .name("New Serie Name")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/series/1", updatedSerieDto)
                )
                .andExpect(status().isBadRequest());
    }
}
