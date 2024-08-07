package springsamurais.toyswapbackend.service.Comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.exception.CommentFailedToSaveException;
import springsamurais.toyswapbackend.exception.CommentNotFoundException;
import springsamurais.toyswapbackend.exception.ListingNotFoundException;
import springsamurais.toyswapbackend.exception.MemberNotFoundException;
import springsamurais.toyswapbackend.model.Comment;
import springsamurais.toyswapbackend.model.CommentDTO;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.CommentsRepository;
import springsamurais.toyswapbackend.repository.ListingRepository;
import springsamurais.toyswapbackend.repository.MemberRepository;

import java.util.Date;
import java.util.List;

@Service
public class CommentsServiceImplementation implements CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentsRepository.findByCommenterId(userId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("No comments found with user ID " + userId);
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsByListingId(Long listingId) {
        List<Comment> comments = commentsRepository.findByListingId(listingId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("No comments found with listing ID " + listingId);
        }
        return comments;
    }

    @Override
    public Comment saveComment(CommentDTO commentDTO) {
        try {
            Member memCommenter = memberRepository.findById(commentDTO.getCommenterId())
                    .orElseThrow(() -> new MemberNotFoundException("Member not found with ID " + commentDTO.getCommenterId()));

            Listing listing = listingRepository.findById(commentDTO.getListingId())
                    .orElseThrow(() -> new ListingNotFoundException("Listing not found with ID " + commentDTO.getListingId()));

            Comment comment = commentDTO.toEntity(memCommenter, listing);

            return commentsRepository.save(comment);
        } catch (ListingNotFoundException | MemberNotFoundException e) {
            throw new CommentFailedToSaveException("Comment failed to save: " + e.getMessage() + " " + e.getClass());
        }
    }

    @Override
    public String deleteCommentByCommentId(Long commentId) {
        if (!commentsRepository.existsById(commentId)) {
            throw new CommentNotFoundException("no comment with comment ID " + commentId);
        }
        commentsRepository.deleteById(commentId);
        return "Comment deleted";
    }

    @Override
    public String deleteCommentsByListingId(Long listingId) {
        List<Comment> comments = commentsRepository.findByListingId(listingId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("no comment with listing ID " + listingId);
        }
        commentsRepository.deleteAll(comments);
        return "Comment deleted";
    }

    @Override
    public String deleteCommentsByUserId(Long userId) {
        List<Comment> comments = commentsRepository.findByCommenterId(userId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("No comment with user ID " + userId);
        }
        commentsRepository.deleteAll(comments);
        return "Comment deleted";
    }

}
