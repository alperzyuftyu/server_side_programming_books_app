package be.thomasmore.bookserver.services;


import be.thomasmore.bookserver.model.BookClub;
import be.thomasmore.bookserver.model.converters.BookClubDTOConverter;
import be.thomasmore.bookserver.model.dto.BookClubDTO;
import be.thomasmore.bookserver.repositories.BookClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookClubService {
    private final BookClubRepository bookClubRepository;
    private final BookClubDTOConverter bookClubDTOConverter;



    public BookClubService(BookClubRepository bookClubRepository, BookClubDTOConverter bookClubDTOConverter) {
        this.bookClubRepository = bookClubRepository;
        this.bookClubDTOConverter = bookClubDTOConverter;

    }

    public List<BookClubDTO> findAll(){
        final List<BookClub> bookClubs = bookClubRepository.findAll();

        return bookClubs.stream()
                .map(bookClubDTOConverter::convertToDto).toList();
    }

    public List<BookClub> searchBookClubs(String filter,String language){
        return bookClubRepository.searchBookClubs(filter,language);

    }
}
