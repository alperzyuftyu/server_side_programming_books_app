package be.thomasmore.bookserver.controllers.books;


import be.thomasmore.bookserver.AbstractIntegrationTest;
import be.thomasmore.bookserver.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("/sql/books/create_book.sql")
@Sql(
        scripts = "/sql/books/clean_books.sql",
        executionPhase = AFTER_TEST_METHOD
)

public class BookControllerDeleteTest extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @WithMockUser
    public void deleteBook() throws Exception {

        assertThat(bookRepository.existsById(1)).isTrue();

        mockMvc.perform(
                        getMockRequestDelete("/api/books/{id}", 1)
                )
                .andExpect(status().isNoContent());

        assertThat(bookRepository.existsById(1)).isFalse();
    }

    @Test
    @WithMockUser
    public void deleteBook_notFound() throws Exception {

        mockMvc.perform(
                        getMockRequestDelete("/api/books/{id}",999)
                )
                .andExpect(status().isNotFound());
    }
}
