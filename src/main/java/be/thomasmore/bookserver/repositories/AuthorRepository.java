package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.Author;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository  extends CrudRepository<Author, Integer> {
    Optional<Author> findByName(String authorName);

    @NonNull
    List<Author> findAll();


}
