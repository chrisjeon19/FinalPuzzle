package org.example;
import services.AzureImageService;
import java.util.Scanner;


public class Main {
  public static void main(String[] args) throws Exception { // Propagate the exception
    String apiKey = "Ea20dZP3G57Dm2u3nJWL";
    String endpoint = "https://myresourceforfinal.openai.azure.com/";

    AzureImageService imageService = new AzureImageService(apiKey, endpoint);
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter a prompt for the image:");
    String userPrompt = scanner.nextLine();

    String imageUrl = imageService.fetchImageUrl(userPrompt);
    if (imageUrl.startsWith("Error:")) {
      System.err.println(imageUrl); // Error message
    } else {
      System.out.println("Image URL: " + imageUrl);
      // Continue processing the image...
    }

    scanner.close();
  }
}
