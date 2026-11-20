/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.lmalecic.parsers.rss;

import app.lmalecic.model.Article;
import app.lmalecic.model.Category;
import app.lmalecic.model.Creator;
import app.lmalecic.utilities.FileUtils;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jsoup.Jsoup;

/**
 *
 * @author lmalecic
 */
public class ArticleParser {

    private static final String RSS_URL = "https://www.thoroughbreddailynews.com/feed/";
    private static final String ATTRIBUTE_URL = "url";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";

    public static Stream<Article> parse() throws Exception {
        AtomicReference<String> imageUrl = new AtomicReference<>();
        
        return new RssReader()
                .setConnectionTimeout(Duration.ZERO)
                .setReadTimeout(Duration.ZERO)
                .addItemExtension("dc:creator", Item::setAuthor)
                .addItemExtension("image", (item, url) -> {
                    imageUrl.set(url);
                })
                .read(RSS_URL)
                .filter(item -> Arrays.asList(
                                    item.getTitle(),
                                    item.getLink(),
                                    item.getPubDate(),
                                    item.getAuthor(),
                                    item.getGuid())
                                .stream()
                                .allMatch(Optional::isPresent)
                )
                .map(item -> {
                    List<Category> categories = item.getCategories()
                            .stream()
                            .map(categoryName -> new Category(categoryName))
                            .toList();
                    
                    Creator creator = new Creator(item.getAuthor().get());
                    
                    String guid = "";
                    try {
                        URI guidUri = new URI(item.getGuid().get());
                        String query = guidUri.getQuery();
                        Map<String, String> queryPairs = Arrays.stream(query.split("&"))
                            .map(param -> param.split("="))
                            .collect(Collectors.toMap(
                                pair -> URLDecoder.decode(pair[0], StandardCharsets.UTF_8),
                                pair -> pair.length > 1 ? URLDecoder.decode(pair[1], StandardCharsets.UTF_8) : ""
                            ));
                        
                        var guidString = queryPairs.get("p");
                        if (guidString != null) {
                            guid = guidString;
                        } else {
                            throw new Exception("Invalid guid parameter from guid tag; got null!");
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ArticleParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    var article = new Article(
                        item.getTitle().get(),
                        item.getLink().get(),
                        creator,
                        OffsetDateTime.parse(item.getPubDate().get(), DateTimeFormatter.RFC_1123_DATE_TIME).toLocalDateTime(),
                        Jsoup.parse(item.getDescription().get()).text(),
                        guid
                    );
                    
                    article.setCategories(categories);
                    
                    try {
                        var imageBytes = ArticleParser.downloadImageBytes(imageUrl.get());
                        article.setImage(imageBytes);
                    } catch (Exception ex) {
                        Logger.getLogger(ArticleParser.class.getName()).log(Level.SEVERE, "Failed to set image for article " + article.getTitle(), ex);
                    }
                    
                    return article;
                })
                .filter(article -> {
                    return !article.getGuid().isEmpty();
                });
    }
    
    public static byte[] downloadImageBytes(String imageUrl) throws Exception {
        System.out.println("Downloading from: " + imageUrl);

        URL url = URI.create(imageUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // IMPORTANT: Many servers block requests without User-Agent
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();
        System.out.println("Response code: " + responseCode);

        if (responseCode != 200) {
            throw new IOException("HTTP error code: " + responseCode + " for URL: " + imageUrl);
        }

        try (
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[4096];
            int n;
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }

            byte[] result = output.toByteArray();
            System.out.println("Successfully downloaded " + result.length + " bytes");
            return result;
        } finally {
            connection.disconnect();
        }
    }

}
