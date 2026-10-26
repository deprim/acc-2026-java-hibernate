package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.enums.BorrowedStatus;
import bootcamp.hibernate_practical.exception.BookAlreadyBorrowedException;
import bootcamp.hibernate_practical.exception.BookIsNotBorrowedException;
import bootcamp.hibernate_practical.exception.BookNotAavailableException;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                true,
                BorrowedStatus.AVAILABLE
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() { // I would prefer to use a Page<BookResponse> object here, but it's outside of TODO scope
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::mapToResponse)
                .toList();
    }


    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Transactional
    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setAvailable(request.isAvailable());
        book.setBorrowedStatus(request.getBorrowedStatus());

        Book updatedBook = bookRepository.save(book);
        return mapToResponse(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.delete(book);
    }

    public List<BookResponse> findByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findAvailableBooks() {
        return bookRepository.findByAvailableTrue().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findByPartialTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse)
                .toList();
    }

    //    Available - book is in store (exists). And borrowed status - could be an enum tracking it's lifecycle within the store.
    //    Think about it this way - Available book can be bought. But if it's both available and borrowed - customer can't buy it.


    @Transactional
    public BookResponse borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

       if (!book.isAvailable()) {
           throw new BookNotAavailableException("Book is not available");
       }

       if (book.getBorrowedStatus() == BorrowedStatus.BORROWED) {
           throw new BookAlreadyBorrowedException("Book is already borrowed");
       }

       book.setBorrowedStatus(BorrowedStatus.BORROWED);
       bookRepository.save(book);
       return mapToResponse(book);

    }

    @Transactional
    public BookResponse returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (book.getBorrowedStatus() == BorrowedStatus.BORROWED) {
            book.setBorrowedStatus(BorrowedStatus.AVAILABLE);
            bookRepository.save(book);
            return mapToResponse(book);
        } else {
            throw new BookIsNotBorrowedException("Book is not borrowed");
        }

    }

    private BookResponse mapToResponse(Book book) {

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable(),
                book.getBorrowedStatus());
    }
}
