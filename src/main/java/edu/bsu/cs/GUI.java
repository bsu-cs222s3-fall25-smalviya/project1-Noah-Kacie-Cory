package edu.bsu.cs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLConnection;

public class GUI extends Application {

    private final TextField articleInput = new TextField();
    private final Button searchButton = new Button("Search");
    private final VBox resultsBox = new VBox(5);
    private final Label redirectLabel = new Label();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 10;");

        Label title = new Label("Wikipedia Revision Viewer");

        HBox inputRow = new HBox(5, new Label("Article:"), articleInput, searchButton);

        searchButton.setOnAction(e -> handleSearch());

        root.getChildren().addAll(title, inputRow, redirectLabel, resultsBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Wikipedia GUI");
        primaryStage.show();
    }
    private void handleSearch() {
        String article = articleInput.getText().trim();
        if (article.isEmpty()) {
            showAlert("Input Error", "Please enter an article name.");
            return;
        }

        searchButton.setDisable(true);
        articleInput.setDisable(true);
        resultsBox.getChildren().clear();
        redirectLabel.setText("");

        new Thread(() -> {
            try {
                
                URLConnection connection = GetJsonData.connectToWikipedia(article);
                String json = GetJsonData.readJsonAsStringFrom(connection);
                Platform.runLater(() -> parseAndDisplayResults(json));
            } catch (IOException e) {
                Platform.runLater(() -> showAlert("Network Error", "Could not connect to Wikipedia."));
            } finally {
                Platform.runLater(() -> {
                    searchButton.setDisable(false);
                    articleInput.setDisable(false);
                });
            }
        }).start();
    }
    private void parseAndDisplayResults(String rawJsonData) {
        JSONObject root = new JSONObject(rawJsonData);
        JSONObject query = root.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");

        String pageId = pages.keys().next();
        JSONObject page = pages.getJSONObject(pageId);

        if (pageId.equals("-1")) {
            showAlert("Page Not Found", "This Wikipedia article does not exist.");
            return;
        }

       
        if (page.has("title") && !articleInput.getText().equalsIgnoreCase(page.getString("title"))) {
            redirectLabel.setText("Redirected to: " + page.getString("title"));
        }

        if (!page.has("revisions")) {
            resultsBox.getChildren().add(new Label("No revisions available."));
            return;
        }

          JSONArray revisions = page.getJSONArray("revisions");
        for (int i = 0; i < revisions.length(); i++) {
            JSONObject rev = revisions.getJSONObject(i);
            String timestamp = rev.optString("timestamp", "N/A");
            String user = rev.optString("user", "N/A");
            resultsBox.getChildren().add(new Label((i + 1) + ". " + user + " at " + timestamp));
        }
    }

