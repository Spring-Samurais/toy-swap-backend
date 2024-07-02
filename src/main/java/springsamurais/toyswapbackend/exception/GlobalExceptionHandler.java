package springsamurais.toyswapbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * GlobalExceptionHandler handles all exceptions thrown by the application and provides appropriate HTTP responses.
 * A builder patter was chosen to keep consistency  on the response entity.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles ListingNotFoundException.
     * This exception is thrown when a requested listing is not found in the database.
     *
     * @param e       the ListingNotFoundException
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(ListingNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleListingNotFoundException(ListingNotFoundException e, WebRequest request) {
        logger.error("ListingNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Listing Not Found", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles MemberNotFoundException.
     * This exception is thrown when a requested member is not found in the database.
     *
     * @param e       the MemberNotFoundException
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(MemberNotFoundException e, WebRequest request) {
        logger.error("MemberNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Member Not Found", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles ListingFailedToSaveException.
     * This exception is thrown when there is a failure in saving a listing to the database.
     *
     * @param e       the ListingFailedToSaveException
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(ListingFailedToSaveException.class)
    public ResponseEntity<ErrorResponse> handleListingFailedToSaveException(ListingFailedToSaveException e, WebRequest request) {
        logger.error("ListingFailedToSaveException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Listing Failed to Save", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles CommentNotFoundException.
     * This exception is thrown when a requested comment is not found in the database.
     *
     * @param e       the CommentNotFoundException
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 404 (Not Found)
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCommentNotFoundException(CommentNotFoundException e, WebRequest request) {
        logger.error("CommentNotFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "Comment Not Found", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles CommentFailedToSaveException.
     * This exception is thrown when there is a failure in saving a comment to the database.
     *
     * @param e       the CommentFailedToSaveException
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(CommentFailedToSaveException.class)
    public ResponseEntity<ErrorResponse> handleCommentFailedToSaveException(CommentFailedToSaveException e, WebRequest request) {
        logger.error("CommentFailedToSaveException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Comment Failed to Save", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles ImageFailedToUpload.
     * This exception is thrown when there is a failure in uploading an image.
     *
     * @param e       the ImageFailedToUpload
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 422 (Unprocessable Entity)
     */
    @ExceptionHandler(ImageFailedToUpload.class)
    public ResponseEntity<ErrorResponse> handlerImageFailedToUpload(ImageFailedToUpload e, WebRequest request) {
        logger.error("ImageFailedToUpload: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.value(), "Image Failed to Upload", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handles all other exceptions.
     * This method catches any exceptions that are not explicitly handled by other methods.
     *
     * @param e       the Exception
     * @param request the WebRequest
     * @return a ResponseEntity containing an ErrorResponse with HTTP status 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e, WebRequest request) {
        logger.error("Exception: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected Internal Server Error", e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @AllArgsConstructor
    @Data
    public static class ErrorResponse{
        private LocalDateTime timestamp;
        private int statusCode;
        private String error;
        private String message;
        private String path;
    }

}
