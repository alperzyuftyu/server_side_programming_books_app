package be.thomasmore.bookserver.services;


import be.thomasmore.bookserver.model.Author;
import be.thomasmore.bookserver.model.converters.AuthorDTOConverter;
import be.thomasmore.bookserver.model.dto.AuthorDTO;
import be.thomasmore.bookserver.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {


    private final AuthorRepository authorRepository;
    private final AuthorDTOConverter authorDTOConverter;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorDTOConverter authorDTOConverter) {
        this.authorRepository = authorRepository;
        this.authorDTOConverter = authorDTOConverter;
    }

    public List<AuthorDTO> findAll() {
         final List<Author> authors = authorRepository.findAll();

        return authors.stream()
                .map(authorDTOConverter::convertToDto)
                .toList();
    }
    public AuthorDTO findById(int id) {
        Optional<Author> author = authorRepository.findById(id);
        return authorDTOConverter.convertToDto(author.get());
    }
}
