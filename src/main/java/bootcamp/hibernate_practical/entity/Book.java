package bootcamp.hibernate_practical.entity;

import bootcamp.hibernate_practical.enums.BorrowedStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private boolean available;
    private BorrowedStatus borrowedStatus;

    public Book(String title, String author, String genre, int publicationYear, boolean available, BorrowedStatus borrowedStatus) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.available = available;
        this.borrowedStatus = borrowedStatus;
    }
}
