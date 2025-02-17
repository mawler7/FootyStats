package com.footystars.service.api;

import com.google.common.util.concurrent.RateLimiter;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageFetcherService {

    // Ustawiamy RateLimiter na 7 żądań na sekundę (czyli około 420 na minutę)
    private final RateLimiter rateLimiter = RateLimiter.create(7.0);

    private static final int MAX_RETRIES = 5;

    /**
     * Pobiera obrazek z podanego remoteUrl i zapisuje go pod lokalną ścieżką localPathStr.
     */
    public void fetchAndStoreImage(String remoteUrl, String localPathStr) {
        int attempt = 0;
        boolean success = false;
        while (attempt < MAX_RETRIES && !success) {
            attempt++;
            rateLimiter.acquire();
            try {
                URL url = new URL(remoteUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == 429) {
                    // Jeśli otrzymamy 429, sprawdzamy nagłówek Retry-After
                    String retryAfter = connection.getHeaderField("Retry-After");
                    int delaySeconds = retryAfter != null ? Integer.parseInt(retryAfter) : attempt * 2;
                    System.out.println("Received 429. Attempt " + attempt + ". Waiting " + delaySeconds + " seconds...");
                    Thread.sleep(delaySeconds * 1000L);
                    continue;
                }
                if (responseCode >= 200 && responseCode < 300) {
                    try (InputStream in = connection.getInputStream()) {
                        Path localPath = Paths.get(localPathStr);
                        Files.createDirectories(localPath.getParent());
                        Files.copy(in, localPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Fetched image: " + remoteUrl + " to " + localPathStr);
                        success = true;
                    }
                } else {
                    System.err.println("Error fetching image (HTTP " + responseCode + "): " + remoteUrl);
                    break;
                }
            } catch (Exception e) {
                System.err.println("Error on attempt " + attempt + " for: " + remoteUrl);
                e.printStackTrace();
                try {
                    Thread.sleep(attempt * 2000L);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        if (!success) {
            System.err.println("Failed to fetch image: " + remoteUrl + " after " + MAX_RETRIES + " attempts.");
        }
    }

    /**
     * Odczytuje plik CSV z lokalnej ścieżki csvFilePath i dla każdego URL-a zapisuje obrazek
     * do lokalnej ścieżki określonej przez localBasePath, przy czym przekształca URL według schematu.
     */
    public void importImagesFromCsv(String csvFilePath, String localBasePath) {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length > 0) {
                    String remoteUrl = nextLine[0].trim();
                    String localRelativePath = remoteUrl
                            .replace("https://media.api-sports.io/football/teams/", "teams/")
                            .replace("https://media.api-sports.io/football/leagues_standings/", "leagues_standings/")
                            .replace("https://media.api-sports.io/football/leagues/", "leagues/")
                            .replace("https://media.api-sports.io/football/flags/", "flags/")
                            .replace("https://media.api-sports.io/football/fixtures/", "fixtures/")
                            .replace("https://media.api-sports.io/football/home_logo/", "fixtures/home/")
                            .replace("https://media.api-sports.io/football/away_logo/", "fixtures/away/")
                            .replace("https://media.api-sports.io/football/fixture_team_stats/", "fixture_team_stats/");
                    String localPath = localBasePath + "/" + localRelativePath;
                    fetchAndStoreImage(remoteUrl, localPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
