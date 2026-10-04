package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends CrudRepository<Book, Integer> {

    Optional<Book> findByTitle(String title);

    @NonNull
    List<Book> findAll();

    List<Book> findByTitleContainingIgnoreCase(String titleKeyWord);

    @Query("""
    select b
    from Book b
    where (:titleKeyWord is null
           or lower(b.title) like lower(concat('%', :titleKeyWord, '%')))
      and (:language is null
           or lower(b.language) like lower(concat('%', :language, '%')))
""")
    List<Book> findByFilters(
            @Param("titleKeyWord") String titleKeyWord,
            @Param("language") String language
    );



}