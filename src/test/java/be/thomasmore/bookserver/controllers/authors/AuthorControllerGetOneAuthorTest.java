package be.thomasmore.bookserver.controllers.authors;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/authors/create_2_authors.sql")
@Sql(scripts = "/sql/authors/clean_authors.sql", executionPhase = AFTER_TEST_METHOD)

public class AuthorControllerGetOneAuthorTest extends AbstractIntegrationTest {
    @Test
    public void findAuthorById() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.books").exists())
                .andExpect(jsonPath("$.books").isArray())
                .andExpect(jsonPath("$.books").isEmpty());

    }
}
