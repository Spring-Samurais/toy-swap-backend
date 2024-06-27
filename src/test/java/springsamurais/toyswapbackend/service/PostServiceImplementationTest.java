package springsamurais.toyswapbackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostServiceImplementationTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    private PostServiceImplementation PSI;


    @Test
    void addListing() {

        Listing listing = new Listing();

    }
}