package springsamurais.toyswapbackend.firebase.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springsamurais.toyswapbackend.firebase.FirebaseService;
import springsamurais.toyswapbackend.firebase.token.FirebaseEntryPoint;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    String tokenTest = "eyJhbGciOiJSUzI1NiIsImtpZCI6Ijg3YmJlMDgxNWIwNjRlNmQ0NDljYWM5OTlmMGU1MGU3MmEzZTQzNzQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI4Nzk2Njg3MDk0NzAtcDZzamFtaXE1MW04aWRqcW50M2xldjlhNjZwMmo4MGcuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI4Nzk2Njg3MDk0NzAtNDhyNHZxOHVqcjlyM3VraTUzaHJ1NXY0OTM3ZXI5OHUuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTA2OTM2OTY3MDA1MTA1NzkyNDEiLCJlbWFpbCI6ImFuZ2Vsc2NobWFsLjFhQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiQW5nZWwgU2NobWFsYmFjaCIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKS2Ytb0g0UkEzakVUUk1xWWVZY2ZhTS1iX0JWSlk0eWFsUFFJT3pfWXhrMWIySFlEaD1zOTYtYyIsImdpdmVuX25hbWUiOiJBbmdlbCIsImZhbWlseV9uYW1lIjoiU2NobWFsYmFjaCIsImlhdCI6MTcyMDQ0NzY3OSwiZXhwIjoxNzIwNDUxMjc5fQ.DvgpGlDq7_4LxhWVvsdtbx0Xpi7Yu39OA9kTZDQ2MXyz4iFOtxJw-u8t5sPw-a8DA1ZmsHciBZgwp0fsKdyyjxgEiBHRFE9JQzhbekF4XF5h6hQmHCdiIKOIhHa2_el8lv2SEvRahApYcNxAdYljlYMdSjfpG_Vk-45xqvEmkefbcXhsfJPmsfAwrM2zWjWQref_shyj2ADin4qYCetgQUGIkc122AmB4xlHf4xiQEC6S_Ho9zOCCbo3_w-Etw77-3Ww9F1GinJVTjV2_qIB6dsayW5i9Pap0EycCZHM7Zka7pd3nVMl5wa7xtogHeru1mJQIUHxjPNyWjQf2qPGoQ";
    @Autowired
    private FirebaseService firebaseService;


    public void VerifyTokenController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PermitAll
    @PostMapping("/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        String idToken = authorizationHeader.replace("Bearer ", "");
        logger.info("Received token: {}", idToken);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(tokenTest);
        String uid = decodedToken.getUid();
        System.out.println(uid);
        /*try {
            FirebaseToken decodedToken = firebaseService.verifyToken(idToken);
            return ResponseEntity.ok(decodedToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");


        }*/
        return ResponseEntity.ok().body(uid);
    }
}