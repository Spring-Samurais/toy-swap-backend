package springsamurais.toyswapbackend.firebase;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseTokenGenerator {

    public static void main(String[] args) {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/fbadmin.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            // Create a custom token for a user
            String uid = "m7eHnuTwTsWZXDOhqHxFFwmRO3j1";
            String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
            System.out.println("Custom Token: " + customToken);

            // Verify the custom token
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(customToken);
            System.out.println("Decoded Token: " + decodedToken);

        } catch (IOException | FirebaseAuthException e) {
            e.printStackTrace();
        }
    }
}