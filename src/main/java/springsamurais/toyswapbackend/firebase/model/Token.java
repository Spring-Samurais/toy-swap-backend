package springsamurais.toyswapbackend.firebase.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class Token extends AbstractAuthenticationToken {

    private String token;

    public Token(String token) {
        super(null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
