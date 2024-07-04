package springsamurais.toyswapbackend.service.Comments;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import springsamurais.toyswapbackend.model.Comment;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.CommentsRepository;
import springsamurais.toyswapbackend.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommentsServiceImplementationTest {

    @Mock
    private CommentsRepository commentsRepository;

    @InjectMocks
    private CommentsServiceImplementation commentsServiceImplementation;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void getCommentsByUserId() {

    }

    @Test
    void getCommentsByListingId() {
    }

    @Test
    void saveComment() {
    }

    @Test
    void deleteCommentByCommentId() {
    }

    @Test
    void deleteCommentsByListingId() {
    }

    @Test
    void deleteCommentsByUserId() {
    }
}