package springsamurais.toyswapbackend.repository;

import org.springframework.data.repository.CrudRepository;
import springsamurais.toyswapbackend.model.Post;

public interface PostInterface extends CrudRepository<Post, Long> {}
