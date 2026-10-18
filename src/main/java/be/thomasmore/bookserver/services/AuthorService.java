package be.thomasmore.bookserver.services;


import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.converters.AuthorDTOConverter;
import be.thomasmore.bookserver.model.converters.AuthorDetailedDTOConverter;
import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.model.dto.AuthorDetailedDTO;
import be.thomasmore.bookserver.repositories.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final AuthorDTOConverter authorDTOConverter;
    private final AuthorDetailedDTOConverter authorDetailedDTOConverter;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorDTOConverter authorDTOConverter, AuthorDetailedDTOConverter authorDetailedDTOConverter) {
        this.authorRepository = authorRepository;
        this.authorDTOConverter = authorDTOConverter;
        this.authorDetailedDTOConverter = authorDetailedDTOConverter;
    }

    public List<AuthorDTO> findAll() {
         final List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(authorDTOConverter::convertToDto)
                .toList();
    }
    public AuthorDetailedDTO findById(int id) {
        Optional<Author> author = authorRepository.findById(id);
        System.out.println(author.get().getDescription());
        return authorDetailedDTOConverter.convertToDto(author.get());
    }
    public AuthorDetailedDTO findByName(String name) {
        Author author = authorRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
        return authorDetailedDTOConverter.convertToDto(author);
    }
}
