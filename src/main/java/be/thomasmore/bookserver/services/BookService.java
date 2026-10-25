package be.thomasmore.bookserver.services;

import be.thomasmore.bookserver.model.Book;
import be.thomasmore.bookserver.model.converters.AuthorDTOConverter;
import be.thomasmore.bookserver.model.converters.BookDTOConverter;
import be.thomasmore.bookserver.model.converters.BookDetailedDTOConverter;
import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.model.dto.BookDTO;
import be.thomasmore.bookserver.model.dto.BookDetailedDTO;
import be.thomasmore.bookserver.repositories.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * Service for book-related business logic.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookDTOConverter bookDTOConverter;
    private final BookDetailedDTOConverter bookDetailedDTOConverter;
    private final AuthorDTOConverter authorDTOConverter;

    public BookService(BookRepository bookRepository,
                       BookDTOConverter bookDTOConverter,
                       BookDetailedDTOConverter bookDetailedDTOConverter,
                       AuthorDTOConverter authorDTOConverter) {
        this.bookRepository = bookRepository;
        this.bookDTOConverter = bookDTOConverter;
        this.bookDetailedDTOConverter = bookDetailedDTOConverter;
        this.authorDTOConverter = authorDTOConverter;
    }

    public List<BookDTO> findAll(String titleKeyWord, String language, String sortBy, String author,int page, int size) {
       // final List<Book> books = titleKeyWord == null
           //     ? bookRepository.findAll()
         //       : bookRepository.findByTitleContainingIgnoreCase(titleKeyWord);

        Sort sort;
        if("publicationYear".equals(sortBy)) {
            sort = Sort.by( "publicationYear");
        }else{
            sort = Sort.by("title");
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        final Page<Book> books =bookRepository.findByFilters(titleKeyWord, language,author,pageable);




        return books.getContent()
                .stream()
                .map(bookDTOConverter::convertToDto)
                .toList();
    }

    public BookDetailedDTO findOne(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Book with id %d does not exist.", id)));

       BookDetailedDTO dto = bookDetailedDTOConverter.convertToDto(book);
        Integer ageInYears = null;
        if(book.getPublicationYear() != null) {
            ageInYears = Year.now().getValue() - book.getPublicationYear();
        }
        return dto.toBuilder().ageInYears(ageInYears).build();
    }

    public List<AuthorDTO> authorsForBook(int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book with id %d not found.".formatted(bookId)));

        return book.getAuthors().stream()
                .map(authorDTOConverter::convertToDto)
                .toList();
    }

    public BookDetailedDTO create(BookDetailedDTO bookDto) {
        if (bookRepository.findByTitleIgnoreCase(bookDto.title()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Book with title %s already exists.".formatted(bookDto.title()));
        }

        // Create DTO without authors for entity conversion
        BookDetailedDTO dtoForConversion = bookDto.toBuilder()
                .authors(null)
                .build();

        final Book entityToSave = bookDetailedDTOConverter.convertToEntity(dtoForConversion);
        entityToSave.setId(0); // Ensure Hibernate treats this as a new entity
        final Book bookSaved = bookRepository.save(entityToSave);
        return bookDetailedDTOConverter.convertToDto(bookSaved);
    }

    public BookDetailedDTO edit(int id, BookDetailedDTO bookDto) {
        if (bookDto.id() != id) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "id in book (%d) does not match id in url (%d).".formatted(bookDto.id(), id));
        }

        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book with id %d not found.".formatted(id)));

        Optional<Book> bookWithSameTitle =
                bookRepository.findByTitleIgnoreCase(bookDto.title());

        if (bookWithSameTitle.isPresent()
                && bookWithSameTitle.get().getId() != id) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Book with title '%s' already exists."
                            .formatted(bookDto.title())
            );
        }

        // Overwrite fields present in bookDto - relations are not touched
        Book bookSaved = bookRepository.save(bookDetailedDTOConverter.convertToEntity(bookDto, bookFromDb));
        return bookDetailedDTOConverter.convertToDto(bookSaved);
    }

    public void delete(int id) {
        if (!bookRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book with id %d not found.".formatted(id));
        }
        bookRepository.deleteById(id);
    }
}
