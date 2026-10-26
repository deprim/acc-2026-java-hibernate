package bootcamp.hibernate_practical.dto;

import bootcamp.hibernate_practical.enums.BorrowedStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must contain maximum of 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author must contain maximum of 255 characters")
    private String author;

    @NotBlank(message = "Genre is required")
    @Size(max = 255, message = "Genre must contain maximum of 255 characters")
    private String genre;

    @Min(value = 1000, message = "Publication year must be greater than 1000")
    @Max(value = 2026, message = "Publication year must be less than 2026")
    private int publicationYear;

    public boolean available;

    @NotNull(message = "Borrowed status is required")
    private BorrowedStatus borrowedStatus;


}
