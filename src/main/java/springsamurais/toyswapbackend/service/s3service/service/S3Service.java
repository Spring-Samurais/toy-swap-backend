package springsamurais.toyswapbackend.service.s3service.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import springsamurais.toyswapbackend.Config;
import springsamurais.toyswapbackend.exception.ImageFailedToUpload;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class S3Service {

    private S3Client s3Client;
    private final Config config = new Config();

    @PostConstruct
    public void init() {
        String accessKeyId = config.getProperty("AWS_ACCESS_KEY_ID");
        String secretAccessKey = config.getProperty("AWS_SECRET_ACCESS_KEY");
        String region = config.getProperty("AWS_REGION_NAME");
        String bucketName = config.getProperty("AWS_BUCKET_NAME");

        if (accessKeyId == null || secretAccessKey == null || region == null || bucketName == null) {
            throw new IllegalStateException("AWS credentials, region, and bucket name must be set in the configuration file. If the file is empty or not exists, contact Angel.");
        }

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    public String uploadImageS3(MultipartFile image) {
        String key = Paths.get(Objects.requireNonNull(image.getOriginalFilename())).getFileName().toString();

        try {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(config.getProperty("AWS_BUCKET_NAME"))
                            .key(key)
                            .contentType(image.getContentType())
                            .build(),
                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(image.getBytes()));
        } catch (S3Exception | IOException e) {
            throw new ImageFailedToUpload("Failed to upload image to S3: " + e.getMessage());
        }
        return s3Client.utilities()
                .getUrl(builder -> builder.bucket(config.getProperty("AWS_BUCKET_NAME")).key(key))
                .toExternalForm();
    }
}