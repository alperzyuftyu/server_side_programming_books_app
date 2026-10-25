package be.thomasmore.bookserver.controllers.books;

import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.model.Book;
import be.thomasmore.bookserver.model.dto.BookDetailedDTO;
import be.thomasmore.bookserver.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/books/create_book.sql")
@Sql(
        scripts = "/sql/books/clean_books.sql",
        executionPhase = AFTER_TEST_METHOD
)


public class BookControllerEditTest extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser
    public void editBook() throws Exception {
        BookDetailedDTO updatedBookDto = BookDetailedDTO.builder()
                .id(1)
                .title("New title")
                .publicationYear(2024)
                .language("Dutch")
                .description("New description")
                .build();

        mockMvc.perform(
                getMockRequestPut("/api/books/1", updatedBookDto)

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New title"))
                .andExpect(jsonPath("$.publicationYear").value(2024))
                .andExpect(jsonPath("$.language").value("Dutch"))
                .andExpect(jsonPath("$.description").value("New description"));

        Book updatedBook = bookRepository.findById(1).orElseThrow();

        assertThat(updatedBook.getTitle()).isEqualTo("New title");
        assertThat(updatedBook.getPublicationYear()).isEqualTo(2024);
        assertThat(updatedBook.getLanguage()).isEqualTo("Dutch");
        assertThat(updatedBook.getDescription()).isEqualTo("New description");
    }

    @Test
    @WithMockUser
    public void editBook_titleCannotBeBlank() throws Exception {

        BookDetailedDTO updatedBookDto = BookDetailedDTO.builder()
                .title("")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/books/1", updatedBookDto)
                )
                .andExpect(status().isBadRequest());

        Book book = bookRepository.findById(1)
                .orElseThrow();

        assertThat(book.getTitle()).isEqualTo("Old title");
    }

    @Test
    @WithMockUser
    public void editBook_titleAlreadyExists() throws Exception {

        BookDetailedDTO updatedBookDto = BookDetailedDTO.builder()
                .id(1)
                .title("1984")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/books/1", updatedBookDto)
                )
                .andExpect(status().isConflict());

        Book book = bookRepository.findById(1)
                .orElseThrow();

        assertThat(book.getTitle()).isEqualTo("Old title");

        assertThat(bookRepository.count()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void editBook_sameTitleOfCurrentBookIsAllowed() throws Exception {

        BookDetailedDTO updatedBookDto = BookDetailedDTO.builder()
                .id(1)
                .title("old title")
                .description("Updated description")
                .build();

        mockMvc.perform(
                        getMockRequestPut("/api/books/1", updatedBookDto)
                )
                .andExpect(status().isOk());
    }

}
