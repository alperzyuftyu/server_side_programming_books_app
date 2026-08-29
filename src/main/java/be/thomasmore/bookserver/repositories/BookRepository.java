package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    @NonNull
    List<Book> findAll();

    List<Book> findByTitleContainingIgnoreCase(String titleKeyWord);

}