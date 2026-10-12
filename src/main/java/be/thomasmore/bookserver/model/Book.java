package be.thomasmore.bookserver.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(exclude = {"authors"})
@ToString(exclude = {"authors"})
@Entity
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @NotBlank(message = "Book Title should not be blank")
    private String title;

    @Min(value = 0, message = "price should not be smaller than 0")
    @Max(value = 200, message = "price should not be greater than 200")
    Integer priceInEur;

    //todo: clean up (with flyway)
    private String author = ""; //this is not normalized but I don't care for this example

    @Min(value = 1000, message = "publicationYear should not be older than 1000")
    @Max(value = 2100, message = "publicationYear should not be in the far future")
    private Integer publicationYear;

    @Column(length = 1000)
    private String description;

    private String language;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "serie_id")
    private Serie serie;

    private Integer sequenceNumber;
}

