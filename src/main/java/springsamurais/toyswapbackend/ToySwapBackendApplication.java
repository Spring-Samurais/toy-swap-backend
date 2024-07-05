package springsamurais.toyswapbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class ToySwapBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToySwapBackendApplication.class, args);
    }

}
