package springsamurais.toyswapbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableConfigurationProperties
public class ToySwapBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToySwapBackendApplication.class, args);
    }

}
