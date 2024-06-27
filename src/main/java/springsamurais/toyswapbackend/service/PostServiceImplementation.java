package springsamurais.toyswapbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.model.Listing;
import springsamurais.toyswapbackend.repository.PostRepository;

@Service
public class PostServiceImplementation  implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Listing addListing(Listing listing) {
        return null;
    }
}
