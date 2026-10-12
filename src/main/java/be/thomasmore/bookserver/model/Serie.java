package be.thomasmore.bookserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor

@Entity
public class Serie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotBlank
    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "serie")
    private List<Book> books = new ArrayList<>();

}
