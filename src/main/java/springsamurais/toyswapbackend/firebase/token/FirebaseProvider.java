package springsamurais.toyswapbackend.firebase.token;

import com.google.firebase.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import springsamurais.toyswapbackend.firebase.model.Token;
import springsamurais.toyswapbackend.firebase.model.UserEntity;


@Component
public class FirebaseProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserRecord userRecord = null;
        try {
            Token token = (Token) authentication;
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getToken(),true);
            String uid = firebaseToken.getUid();
            userRecord = FirebaseAuth.getInstance().getUser(uid);
        } catch (FirebaseAuthException e) {
            logger.error("Failed: {}", getErrorCode(e.getAuthErrorCode()));
            throw new RuntimeException(e);
        }

        return new UserEntity(userRecord);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(Token.class);
    }

    private String getErrorCode(AuthErrorCode errorCode){
            String error;
        switch (errorCode) {
            case INVALID_ID_TOKEN:
                return "Invalid ID token.";
            case PHONE_NUMBER_ALREADY_EXISTS:
                return "Phone number already exists.";
            case TENANT_NOT_FOUND:
                return "Tenant not found.";
            case UID_ALREADY_EXISTS:
                return "UID already exists.";
            case USER_NOT_FOUND:
                return "User not found.";
            default:
                return "Unknown error occurred.";
        }
    }
}
