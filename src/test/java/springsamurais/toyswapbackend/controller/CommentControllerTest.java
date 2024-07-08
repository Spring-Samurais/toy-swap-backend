package springsamurais.toyswapbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springsamurais.toyswapbackend.model.Comment;
import springsamurais.toyswapbackend.model.Member;
import springsamurais.toyswapbackend.repository.CommentsRepository;
import springsamurais.toyswapbackend.service.Comments.CommentsService;
import springsamurais.toyswapbackend.service.Comments.CommentsServiceImplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    @Mock
    private CommentsService commentsService;

    @InjectMocks
    private CommentController commentController;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getCommentsByUserId() throws Exception {

        Member commenter = new Member(1L, "name", "member", "location","password", null);

        Comment mockComment = new Comment();
        mockComment.setCommentId(1L);
        mockComment.setText("very Nice");
        mockComment.setCommenter(commenter);

        when(commentsService.getCommentsByUserId(1L)).thenReturn(Arrays.asList(mockComment));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].commentId").value(1L))
                .andExpect(jsonPath("$[0].text").value("very Nice"))
                .andExpect(jsonPath("$[0].commenter.id").value(1L));

    }

    @Test
    void getCommentsByListingId() throws Exception {

        Member commenter = new Member(1L, "name", "member", "location", "password",null);

        Comment mockComment = new Comment();
        mockComment.setCommentId(1L);
        mockComment.setText("very Nice");
        mockComment.setCommenter(commenter);

        when(commentsService.getCommentsByListingId(1L)).thenReturn(Arrays.asList(mockComment));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments/listing/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].commentId").value(1L))
                .andExpect(jsonPath("$[0].text").value("very Nice"))
                .andExpect(jsonPath("$[0].commenter.id").value(1L));

    }

    @Test
    void saveComment() throws Exception {

        Member commenter = new Member(1L, "name", "member", "location", "password",null);

        Comment mockComment = new Comment();
        mockComment.setCommentId(1L);
        mockComment.setText("very Nice");
        mockComment.setCommenter(commenter);

        when(commentsService.saveComment(any(springsamurais.toyswapbackend.model.CommentDTO.class))).thenReturn(mockComment);

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"text\": \"very Nice\", \"commenter\": { \"id\": 1 } }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId").value(1L))
                .andExpect(jsonPath("$.text").value("very Nice"))
                .andExpect(jsonPath("$.commenter.id").value(1L));

    }

    @Test
    void deleteCommentByCommentId() throws Exception {
        when(commentsService.deleteCommentByCommentId(1L)).thenReturn("Comment deleted" );

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comments/{commentId}", 1L))
                .andExpect(status().isOk());

        verify(commentsService, times(1)).deleteCommentByCommentId(1L);

    }

    @Test
    void deleteCommentsByListingId() throws Exception {

       when(commentsService.deleteCommentsByListingId(1L)).thenReturn("Comment deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comments/listing/{listingId}", 1L))
                .andExpect(status().isOk());

        verify(commentsService, times(1)).deleteCommentsByListingId(1L);
    }

    @Test
    void deleteCommentsByUserId() throws Exception {
        // Mocking the service behavior
        when(commentsService.deleteCommentsByUserId(1L)).thenReturn("Comment deleted");

        // Performing the request and verifying response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/comments/user/{userId}", 1L))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct userId
        verify(commentsService, times(1)).deleteCommentsByUserId(1L);
    }
}