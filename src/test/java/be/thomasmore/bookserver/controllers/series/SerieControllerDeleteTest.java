package be.thomasmore.bookserver.controllers.series;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.repositories.SerieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("/sql/series/create_empty_serie.sql")
@Sql(
        scripts = "/sql/series/clean_series.sql",
        executionPhase = AFTER_TEST_METHOD
)
public class SerieControllerDeleteTest extends AbstractIntegrationTest {

    @Autowired
    private SerieRepository serieRepository;

    @Test
    @WithMockUser
    public void deleteSerie() throws Exception {

        assertThat(serieRepository.existsById(1)).isTrue();

        mockMvc.perform(
                        getMockRequestDelete("/api/series/{id}", 1)
                )
                .andExpect(status().isNoContent());

        assertThat(serieRepository.existsById(1)).isFalse();
    }

    @Test
    @WithMockUser
    public void deleteSerie_notFound() throws Exception {

        mockMvc.perform(
                        getMockRequestDelete("/api/series/{id}", 999)
                )
                .andExpect(status().isNotFound());
    }
}
