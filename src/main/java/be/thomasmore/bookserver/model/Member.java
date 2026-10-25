package be.thomasmore.bookserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class Member {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @NotBlank(message = "Username should not be blank")
    private String username;

    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email should have a valid format")
    private String email;

    private String firstName;
    private String lastName;

    @ManyToMany()
    @JoinTable(name="member_favorite_books",
    joinColumns = @JoinColumn(name="member_id"),
    inverseJoinColumns = @JoinColumn(name="favorite_books_id"))
    private Set<Book> favoriteBooks;
}
