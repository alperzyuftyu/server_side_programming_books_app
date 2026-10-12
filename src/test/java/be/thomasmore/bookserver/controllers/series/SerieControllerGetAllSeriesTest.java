package be.thomasmore.bookserver.controllers.series;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SerieControllerGetAllSeriesTest extends AbstractIntegrationTest {

    @Test
    @Sql("/sql/series/create_serie_with_books.sql")
    @Sql(
            scripts = "/sql/series/clean_series.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void getAllSeries() throws Exception {

        mockMvc.perform(getMockRequestGet("/api/series"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)))

                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
    }

    @Test
    @Sql("/sql/series/create_serie_with_books.sql")
    @Sql(
            scripts = "/sql/series/clean_series.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void getAllSeriesSortedByName() throws Exception {

        mockMvc.perform(
                        getMockRequestGet(
                                "/api/series?sortBy=name"
                        )
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(2)))

                .andExpect(jsonPath("$.content[0].name")
                        .value("Harry Potter"))

                .andExpect(jsonPath("$.content[1].name")
                        .value("MaddAddam-trilogie"))

                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0));
    }
}
