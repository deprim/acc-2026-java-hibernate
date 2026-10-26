package bootcamp.hibernate_practical.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateBookRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title must contain maximum of 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author must contain maximum of 255 characters")
    private String author;

    @NotBlank(message = "Genre is required")
    @Size(max = 255, message = "Genre must contain maximum of 255 characters")
    private String genre;

    @Min(value = 1000, message = "Publication year must be greater or equal to 1000")
    @Max(value = 2026, message = "Publication year must be less or equal to 2026")
    private int publicationYear;
}
