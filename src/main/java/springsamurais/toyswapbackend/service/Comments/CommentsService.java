package springsamurais.toyswapbackend.service.Comments;

import springsamurais.toyswapbackend.model.Comment;
import java.util.List;

public interface CommentsService {
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByListingId(Long listingId);
    Comment saveComment(Comment comment);
    String deleteCommentByCommentId(Long commentId);
    String deleteCommentsByListingId(Long listingId);
    String deleteCommentsByUserId(Long userId);
}
