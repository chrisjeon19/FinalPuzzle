package services;

import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.json.JSONObject;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

public class AzureImageService {
  private final String apiKey;
  private final String endpoint;

  // constructor to load api key and endpoint
  public AzureImageService(String apiKey, String endpoint) {
    this.apiKey = apiKey;
    this.endpoint = endpoint + "/openai/images/generations:invoke";  // DALL·E image generation endpoint
  }

  // fetch the image url from dalle
  public String fetchImageUrl(String userPrompt) throws IOException, InterruptedException {
    // create the request payload with the user prompt
    String payload = "{ \"prompt\": \"" + userPrompt + "\", \"n\": 1, \"size\": \"1024x1024\" }"; // image request of this size

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(endpoint))
      .header("Content-Type", "application/json")
      .header("Authorization", "Bearer " + apiKey)
      .POST(HttpRequest.BodyPublishers.ofString(payload))
      .build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      JSONObject responseJson = new JSONObject(response.body());
      return responseJson.getJSONArray("data").getJSONObject(0).getString("url");  // Get the image URL from the response
    } else {
      throw new IOException("Failed to fetch image: " + response.body());
    }
  }

  // download and save the image
  public BufferedImage downloadImage(String imageUrl, String outputPath) throws IOException {
    URL url = new URL(imageUrl);
    BufferedImage image = ImageIO.read(url);
    ImageIO.write(image, "png", new File(outputPath));  // Save the image as PNG
    return image;
  }
}
