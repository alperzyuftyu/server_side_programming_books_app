package be.thomasmore.bookserver.controllers.authors;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.dto.AuthorDetailedDTO;
import be.thomasmore.bookserver.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/sql/authors/clean_authors.sql", executionPhase = AFTER_TEST_METHOD)

public class AuthorControllerCreateTest  extends AbstractIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser
    public void createAuthor() throws Exception {
        final String AUTHOR_NAME = "George Orwell";

        AuthorDetailedDTO newAuthorDTO = AuthorDetailedDTO.builder()
                .name(AUTHOR_NAME)
                .description("English novelist")
                .build();

        mockMvc.perform(getMockRequestPost("/api/authors", newAuthorDTO)


        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(AUTHOR_NAME))
                .andExpect(jsonPath("$.description").value("English novelist"));

        Author savedAuthor = authorRepository
                .findByName(AUTHOR_NAME)
                .orElseThrow();

        assertThat(savedAuthor.getName()).isEqualTo(AUTHOR_NAME);
        assertThat(savedAuthor.getDescription()).isEqualTo("English novelist");
    }

    @Test
    @WithMockUser
    public void createAuthor_nameCannotBeNull() throws Exception {

        AuthorDetailedDTO newAuthorDTO = AuthorDetailedDTO.builder()
                .name(null)
                .build();

        mockMvc.perform(
                        getMockRequestPost("/api/authors", newAuthorDTO)
                )
                .andExpect(status().isBadRequest());

        assertThat(authorRepository.count()).isEqualTo(0);
    }

    @Test
    @WithMockUser
    public void createAuthor_nameCannotBeEmpty() throws Exception {

        AuthorDetailedDTO newAuthorDTO = AuthorDetailedDTO.builder()
                .name("")
                .build();

        mockMvc.perform(
                        getMockRequestPost("/api/authors", newAuthorDTO)
                )
                .andExpect(status().isBadRequest());

        assertThat(authorRepository.count()).isEqualTo(0);
    }

    @Test
    @WithMockUser
    public void createAuthor_nameCannotBeBlank() throws Exception {

        AuthorDetailedDTO newAuthorDTO = AuthorDetailedDTO.builder()
                .name("   ")
                .build();

        mockMvc.perform(
                        getMockRequestPost("/api/authors", newAuthorDTO)
                )
                .andExpect(status().isBadRequest());

        assertThat(authorRepository.count()).isEqualTo(0);
    }

    @Test
    @WithMockUser
    public void createAuthor_duplicateNameIsAllowed() throws Exception {

        final String AUTHOR_NAME = "George Orwell";

        AuthorDetailedDTO newAuthorDTO = AuthorDetailedDTO.builder()
                .name(AUTHOR_NAME)
                .build();

        mockMvc.perform(getMockRequestPost("/api/authors", newAuthorDTO))
                .andExpect(status().isOk());

        mockMvc.perform(getMockRequestPost("/api/authors", newAuthorDTO))
                .andExpect(status().isOk());

        assertThat(authorRepository.count()).isEqualTo(2);
    }
}
