package springsamurais.toyswapbackend.exception;

public class CommentFailedToSaveException extends RuntimeException {
    public CommentFailedToSaveException (String message) {
        super(message);
    }
}
