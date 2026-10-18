package be.thomasmore.bookserver.controllers.series;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SerieControllerGetOneSerieTest  extends AbstractIntegrationTest {

    @Test
    @Sql("/sql/series/create_serie_with_books.sql")
    @Sql(
            scripts = "/sql/series/clean_series.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void getOneSerie() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/series/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("MaddAddam-trilogie"))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books", hasSize(3)))
                .andExpect(jsonPath("$.books[0].title")
                        .value("Oryx and Crake"))

                .andExpect(jsonPath("$.yearSpan").value(10));

    }
    @Test
    @Sql("/sql/series/create_empty_serie.sql")
    @Sql(
            scripts = "/sql/series/clean_series.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void getOneSerieWithoutBooks() throws Exception {

        mockMvc.perform(getMockRequestGet("/api/series/2"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Empty Serie"))

                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books").isEmpty())

                .andExpect(jsonPath("$.yearSpan").value(nullValue()));
    }

    @Test
    @Sql("/sql/series/create_serie_with_books.sql")
    @Sql(
            scripts = "/sql/series/clean_series.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void getOneSerieWithName() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/series/MaddAddam-trilogie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books", hasSize(3)))
                .andExpect(jsonPath("$.books[0].title")
                        .value("Oryx and Crake"));
    }
}
