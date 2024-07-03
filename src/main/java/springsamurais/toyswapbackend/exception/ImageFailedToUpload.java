package springsamurais.toyswapbackend.exception;

public class ImageFailedToUpload extends RuntimeException {

    public ImageFailedToUpload(String message) {
        super(message);
    }
}
