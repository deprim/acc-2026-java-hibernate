package bootcamp.hibernate_practical.exception;

public class BookNotAavailableException extends RuntimeException {
    public BookNotAavailableException(String message) {
        super(message);
    }
}
