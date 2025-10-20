package za.co.carhire.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ImageDownloader {

    private static final int CONNECT_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);
    private static final int READ_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(30);
    private static final int MAX_IMAGE_SIZE = 10 * 1024 * 1024; // 10MB max
    private static final int BUFFER_SIZE = 8192;

    /**
     * Downloads an image from a URL and returns it as a byte array
     *
     * @param imageUrl the URL of the image to download
     * @return byte array containing the image data, or null if download fails
     */
    public static byte[] downloadImage(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            System.err.println("Image URL is null or empty");
            return null;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();

            // Set timeouts
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            // Set user agent to avoid being blocked by some servers
            connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

            // Follow redirects
            connection.setInstanceFollowRedirects(true);

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to download image from " + imageUrl +
                    ". HTTP response code: " + responseCode);
                return null;
            }

            // Get content type to verify it's an image
            String contentType = connection.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                System.err.println("URL does not point to an image. Content-Type: " + contentType);
                return null;
            }

            // Get content length to avoid downloading extremely large files
            int contentLength = connection.getContentLength();
            if (contentLength > MAX_IMAGE_SIZE) {
                System.err.println("Image size (" + contentLength + " bytes) exceeds maximum allowed size (" + MAX_IMAGE_SIZE + " bytes)");
                return null;
            }

            try (InputStream inputStream = connection.getInputStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                int totalBytesRead = 0;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    totalBytesRead += bytesRead;

                    // Safety check in case content-length was not accurate
                    if (totalBytesRead > MAX_IMAGE_SIZE) {
                        System.err.println("Image download exceeded maximum size during transfer");
                        return null;
                    }

                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] imageData = outputStream.toByteArray();
                System.out.println("Successfully downloaded image from " + imageUrl +
                    " (" + imageData.length + " bytes)");

                return imageData;
            }

        } catch (IOException e) {
            System.err.println("Error downloading image from " + imageUrl + ": " + e.getMessage());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Extracts the image type from a URL or content type
     *
     * @param imageUrl the URL of the image
     * @return the image type (e.g., "jpg", "png", "gif"), or "jpg" as default
     */
    public static String getImageType(String imageUrl) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return "jpg";
        }

        // Try to extract from URL extension
        String lowerUrl = imageUrl.toLowerCase();
        if (lowerUrl.contains(".png")) {
            return "png";
        } else if (lowerUrl.contains(".jpg") || lowerUrl.contains(".jpeg")) {
            return "jpg";
        } else if (lowerUrl.contains(".gif")) {
            return "gif";
        } else if (lowerUrl.contains(".webp")) {
            return "webp";
        } else if (lowerUrl.contains(".bmp")) {
            return "bmp";
        }

        // Default to jpg
        return "jpg";
    }

    /**
     * Extracts a simple name for the image from the URL
     *
     * @param imageUrl the URL of the image
     * @param carBrand the brand of the car (used as fallback)
     * @param carModel the model of the car (used as fallback)
     * @return a descriptive name for the image
     */
    public static String getImageName(String imageUrl, String carBrand, String carModel) {
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            return (carBrand + "-" + carModel).toLowerCase().replace(" ", "-");
        }

        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();

            // Extract filename from path
            int lastSlash = path.lastIndexOf('/');
            if (lastSlash >= 0 && lastSlash < path.length() - 1) {
                String filename = path.substring(lastSlash + 1);

                // Remove query parameters if any
                int queryIndex = filename.indexOf('?');
                if (queryIndex > 0) {
                    filename = filename.substring(0, queryIndex);
                }

                return filename;
            }
        } catch (Exception e) {
            // Fall through to default
        }

        // Default to brand-model format
        return (carBrand + "-" + carModel).toLowerCase().replace(" ", "-");
    }

    /**
     * Downloads an image with retry logic
     *
     * @param imageUrl the URL of the image to download
     * @param maxRetries maximum number of retry attempts
     * @return byte array containing the image data, or null if all attempts fail
     */
    public static byte[] downloadImageWithRetry(String imageUrl, int maxRetries) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            byte[] imageData = downloadImage(imageUrl);

            if (imageData != null) {
                return imageData;
            }

            if (attempt < maxRetries) {
                System.out.println("Retry attempt " + attempt + " of " + maxRetries + " for " + imageUrl);
                try {
                    // Wait before retrying (exponential backoff)
                    Thread.sleep(1000L * attempt);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        }

        System.err.println("Failed to download image after " + maxRetries + " attempts: " + imageUrl);
        return null;
    }
}
