package be.thomasmore.bookserver.controllers.BookClubs;


import be.thomasmore.bookserver.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/bookclubs/create_2_bookclubs.sql")
@Sql(
        scripts = "/sql/bookclubs/clean_bookclubs.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
public class BookClubControllerGetAllBookClubsTest extends AbstractIntegrationTest {


    @Test
    public void getAllBookClubs() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/bookclubs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Leesclub De Boekenwurm"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Antwerp Book Circle"));
    }

    @Sql("/sql/bookclubs/create_2_bookclubs.sql")
    @Sql(
            scripts = "/sql/bookclubs/clean_bookclubs.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )

    @Test
    public void filterBookClubsByFilter() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/bookclubs/search?filter=Circle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Antwerp Book Circle"));

    }

    @Sql("/sql/bookclubs/create_2_bookclubs.sql")
    @Sql(
            scripts = "/sql/bookclubs/clean_bookclubs.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )

    @Test
    public void filterBookClubsByLanguage() throws Exception {
        mockMvc.perform(getMockRequestGet("/api/bookclubs/search?language=Nederlands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Leesclub De Boekenwurm"));
    }
}
