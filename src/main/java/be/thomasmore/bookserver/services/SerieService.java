package be.thomasmore.bookserver.services;

import be.thomasmore.bookserver.model.Book;
import be.thomasmore.bookserver.model.Serie;
import be.thomasmore.bookserver.model.converters.BookDTOConverter;
import be.thomasmore.bookserver.model.converters.SerieDTOConverter;
import be.thomasmore.bookserver.model.converters.SerieDetailedDTOConverter;
import be.thomasmore.bookserver.model.dto.SerieDTO;
import be.thomasmore.bookserver.model.dto.SerieDetailedDTO;
import be.thomasmore.bookserver.model.dto.SeriePagedResponseDTO;
import be.thomasmore.bookserver.repositories.SerieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class SerieService {

    private final SerieRepository serieRepository;
    private final SerieDetailedDTOConverter serieDetailedDTOConverter;
    private final BookDTOConverter bookDTOConverter;
    private final SerieDTOConverter serieDTOConverter;


    public SerieService(SerieRepository serieRepository, SerieDetailedDTOConverter serieDetailedDTOConverter, BookDTOConverter bookDTOConverter, SerieDTOConverter serieDTOConverter) {
        this.serieRepository = serieRepository;
        this.serieDetailedDTOConverter = serieDetailedDTOConverter;
        this.bookDTOConverter = bookDTOConverter;
        this.serieDTOConverter = serieDTOConverter;
    }
    private Integer calculateYearSpan(Serie serie) {

        List<Book> books = serie.getBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getPublicationYear).reversed())
                .toList();

        if (books.isEmpty()) {
            return null;
        }

        Book firstBook = books.get(0);
        Book lastBook = books.get(books.size() - 1);

        Integer firstYear = firstBook.getPublicationYear();
        Integer lastYear = lastBook.getPublicationYear();

        if (firstYear == null || lastYear == null) {
            return null;
        }

        return firstYear - lastYear;
    }

    public SerieDetailedDTO findOne(int id) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Book with id %d does not exist.", id)));

        Integer yearSpan = calculateYearSpan(serie);
        SerieDetailedDTO dto = serieDetailedDTOConverter.convertToDto(serie);

        return dto.toBuilder().yearSpan(yearSpan).build();
    }
    public SerieDetailedDTO findByName(String name) {
        Serie serie = serieRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serie not found"));
        Integer yearSpan = calculateYearSpan(serie);

        SerieDetailedDTO dto = serieDetailedDTOConverter.convertToDto(serie);

        return dto.toBuilder().yearSpan(yearSpan).build();
    }

    public SeriePagedResponseDTO findAll(
            int page,
            int size,
            String name,
            Integer books,
            String sortBy) {
        Sort sort;
        if ("name".equals(sortBy)) {
            sort = Sort.by("name");
        } else {
            sort = Sort.by("id");
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        final Page<Serie> pages = serieRepository.findByNameContainingIgnoreCase(pageable,name,books);

        List<SerieDTO> srieDTOs = pages.getContent().stream()
                .map(serieDTOConverter::convertToDTO)
                .toList();



        return SeriePagedResponseDTO.builder()
                .content(srieDTOs)
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .currentPage(pages.getNumber())
                .build();
    }
}
