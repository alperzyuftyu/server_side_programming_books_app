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

@Sql("/sql/authors/create_author_with_description.sql")
@Sql(
        scripts = "/sql/authors/clean_authors.sql",
        executionPhase = AFTER_TEST_METHOD
)

public class AuthorControllerEditTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @WithMockUser
    public void editAuthor() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(1)
                .name("New Author Name")
                .description("New description")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/1", updatedAuthorDto)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Author Name"))
                .andExpect(jsonPath("$.description").value("New description"));

        Author updatedAuthor = authorRepository.findById(1)
                .orElseThrow();

        assertThat(updatedAuthor.getName())
                .isEqualTo("New Author Name");

        assertThat(updatedAuthor.getDescription())
                .isEqualTo("New description");
    }

    @Test
    @WithMockUser
    public void editAuthor_nameCannotBeNull() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(1)
                .name(null)
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/1", updatedAuthorDto)
                )
                .andExpect(status().isBadRequest());

        Author author = authorRepository.findById(1)
                .orElseThrow();

        assertThat(author.getName())
                .isEqualTo("Margaret Atwood");
    }

    @Test
    @WithMockUser
    public void editAuthor_nameCannotBeEmpty() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(1)
                .name("")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/1", updatedAuthorDto)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void editAuthor_notFound() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(999)
                .name("Some Author")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/999", updatedAuthorDto)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void editAuthor_idMustMatchUrl() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(2)
                .name("New Author Name")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/1", updatedAuthorDto)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void editAuthor_duplicateNameIsAllowed() throws Exception {

        AuthorDetailedDTO updatedAuthorDto = AuthorDetailedDTO.builder()
                .id(1)
                .name("Existing Name")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/authors/1", updatedAuthorDto)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Existing Name"));

        Author updatedAuthor = authorRepository.findById(1)
                .orElseThrow();

        assertThat(updatedAuthor.getName())
                .isEqualTo("Existing Name");
    }
}
