package be.thomasmore.bookserver.repositories;

import be.thomasmore.bookserver.model.BookClub;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookClubRepository extends CrudRepository<BookClub, Integer> {

    @NonNull
    List<BookClub> findAll();

    @Query("""
SELECT  bc FROM BookClub bc
WHERE(:filter IS NULL
       OR LOWER(bc.name) LIKE LOWER(CONCAT('%',:filter,'%'))
       OR LOWER(bc.meetingPlace) LIKE LOWER(CONCAT('%',:filter,'%'))
       OR LOWER(bc.city) LIKE LOWER(CONCAT('%',:filter,'%'))
       OR LOWER(bc.language) LIKE LOWER(CONCAT('%',:filter,'%'))
       )AND(
       :language IS NULL OR bc.language = :language
       )
""")
    List<BookClub> searchBookClubs(
            @Param("filter") String filter,
            @Param("language") String language
    );
}
