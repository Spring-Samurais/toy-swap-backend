package springsamurais.toyswapbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.model.Comment;
import springsamurais.toyswapbackend.service.Comments.CommentsService;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUserId(@PathVariable Long userId) {
        List<Comment> comments = commentsService.getCommentsByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Comment>> getCommentsByListingId(@PathVariable Long listingId) {
        List<Comment> comments = commentsService.getCommentsByListingId(listingId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> saveComment(@RequestBody Comment comment) {
        Comment commentSaved = commentsService.saveComment(comment);
        return new ResponseEntity<>(commentSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentByCommentId(@PathVariable Long commentId) {
        String deleteMessage= commentsService.deleteCommentByCommentId(commentId);
        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }

    @DeleteMapping("/listing/{listingId}")
    public ResponseEntity<String> deleteCommentsByListingId(@PathVariable Long listingId) {
       String deleteMessage= commentsService.deleteCommentsByListingId(listingId);
        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteCommentsByUserId(@PathVariable Long userId) {
       String deleteMessage= commentsService.deleteCommentsByUserId(userId);
        return new ResponseEntity<>(deleteMessage, HttpStatus.OK);
    }


}
