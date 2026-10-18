package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.Serie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SerieRepository extends CrudRepository<Serie, Integer> {
    Optional<Serie> findByName(String Name);
    @Query("""
    select s
    from Serie s
    where (:name is null
           or :name = ''
           or lower(s.name) like lower(concat('%', :name, '%')))
           and (:books is null or size(s.books) >= :books)
      
      
""")



    Page<Serie> findByNameContainingIgnoreCase(Pageable pageable,
                                               @Param("name") String name,
                                               @Param("books") Integer books);
}
