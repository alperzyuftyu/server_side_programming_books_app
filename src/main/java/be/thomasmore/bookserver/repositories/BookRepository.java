package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    select distinct b
    from Book b
    left join b.authors a
    where (:titleKeyWord is null
           or :titleKeyWord = ''
           or lower(b.title) like lower(concat('%', :titleKeyWord, '%')))
      and (:language is null
           or :language = ''
           or lower(b.language) like lower(concat('%', :language, '%')))
      and (:author is null
           or :author = ''
           or lower(a.name) like lower(concat('%', :author, '%')))
""")
    Page<Book> findByFilters(
            @Param("titleKeyWord") String titleKeyWord,
            @Param("language") String language,
            @Param("author") String author,
            Pageable pageable
    );


}