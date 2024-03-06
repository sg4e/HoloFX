/*
 * The MIT License (MIT)
 * Copyright (c) 2024 sg4e
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package moe.maika.holofx;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import moe.maika.holofx.json.Frame;

public class MainController {

    private static final String API_KEY_FILE = "apikey.txt";

    @FXML
    private Button connectAndRefreshButton;
    @FXML
    private VBox streamBox;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private String apiKey;

    public MainController() {
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            apiKey = Files.readString(Paths.get(API_KEY_FILE));
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void buttonClicked() {
        connectAndRefreshButton.setDisable(true);
        connectAndRefreshButton.setText("Refreshing");
        queryHolodex();
    }

    private void resetButton() {
        connectAndRefreshButton.setDisable(false);
        connectAndRefreshButton.setText("Refresh");
    }

    private void queryHolodex() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://holodex.net/api/v2/live"))
                .header("X-APIKEY", apiKey)
                .build();
        httpClient.sendAsync(request, BodyHandlers.ofString(Charset.forName("utf8")))
                .thenApply(HttpResponse::body)
                .thenAccept(str -> {
                    try {
                        List<Frame> frames = objectMapper.readValue(str,
                                objectMapper.getTypeFactory().constructCollectionType(List.class, Frame.class));
                        List<Frame> goodStreams = frames.stream()
                                .filter(f -> "hololive".equalsIgnoreCase(f.channel().org()))
                                .filter(f -> !f.channel().suborg().toLowerCase().contains("holostars"))
                                .collect(Collectors.toList());
                                //.forEach(f -> System.out.println(String.format("%s: %s", f.channel().englishName(), f.title())));
                        Platform.runLater(() -> {
                            streamBox.getChildren().clear();
                            goodStreams.forEach(stream -> {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("stream.fxml"));
                                try {
                                    Node element = loader.load();
                                    StreamController controller = loader.getController();
                                    controller.setTitle(stream.title());
                                    controller.setStreamerName(stream.channel().englishName());
                                    streamBox.getChildren().add(element);
                                }
                                catch(Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        });
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                        Platform.runLater(() -> resetButton());
                    }
                });
    }
    
}
