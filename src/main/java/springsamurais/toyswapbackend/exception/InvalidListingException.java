package springsamurais.toyswapbackend.exception;

public class InvalidListingException extends RuntimeException {
    public InvalidListingException(String message) {
        super(message);
    }
}
