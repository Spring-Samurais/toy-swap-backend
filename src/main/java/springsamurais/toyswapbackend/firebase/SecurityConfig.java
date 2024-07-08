package springsamurais.toyswapbackend.firebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springsamurais.toyswapbackend.firebase.token.FirebaseFilter;
import springsamurais.toyswapbackend.firebase.token.FirebaseProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final FirebaseFilter firebaseFilter;
    private final FirebaseProvider firebaseProvider;

    public SecurityConfig(FirebaseFilter firebaseFilter, FirebaseProvider firebaseProvider) {
        this.firebaseFilter = firebaseFilter;
        this.firebaseProvider = firebaseProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/verifyToken").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}