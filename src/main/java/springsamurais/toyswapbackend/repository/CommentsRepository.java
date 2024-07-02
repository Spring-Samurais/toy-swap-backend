package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import springsamurais.toyswapbackend.model.Comment;
import java.util.List;

public interface CommentsRepository extends CrudRepository <Comment, Long> {
    List<Comment> findByCommenterId(Long Id);
    List<Comment> findByListingId(Long listingId);
}

