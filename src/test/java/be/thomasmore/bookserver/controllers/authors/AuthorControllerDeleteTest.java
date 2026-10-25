package be.thomasmore.bookserver.controllers.authors;


import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/authors/create_author_with_description.sql")
@Sql(
        scripts = "/sql/authors/clean_authors.sql",
        executionPhase = AFTER_TEST_METHOD
)

public class AuthorControllerDeleteTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser
    public void deleteAuthor() throws Exception {

        assertThat(authorRepository.existsById(1)).isTrue();

        mockMvc.perform(
                        getMockRequestDelete("/api/authors/{id}", 1)
                )
                .andExpect(status().isNoContent());

        assertThat(authorRepository.existsById(1)).isFalse();
    }

    @Test
    @WithMockUser
    public void deleteAuthor_notFound() throws Exception {

        mockMvc.perform(
                        getMockRequestDelete("/api/author/{id}",999)
                )
                .andExpect(status().isNotFound());
    }
}

