package springsamurais.toyswapbackend.firebase.controller;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.firebase.FirebaseService;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private FirebaseService firebaseService;

    @PermitAll
    @GetMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        String idToken = authorizationHeader.replace("Bearer ", "");
        try {
            FirebaseToken decodedToken = firebaseService.verifyToken(idToken);
            return ResponseEntity.ok(decodedToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}