package springsamurais.toyswapbackend.service.imgurapi.service;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;
import springsamurais.toyswapbackend.exception.ImageFailedToUpload;

@Service
public class ImgurService {

    private final String clientId = "6e3fc450700b556";

    public String uploadImage(String imageBase64, String imageTitle) throws ImageFailedToUpload {
        HttpResponse<JsonNode> response = Unirest.post("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + clientId)
                .field("image", imageBase64)
                .field("title", imageTitle)
                .asJson();

        if (response.getStatus() == 200) {
            return response.getBody().getObject().getJSONObject("data").getString("link");
        } else {
            throw new ImageFailedToUpload ("Failed to upload image to Imgur: " + response.getStatusText());
        }
    }

}