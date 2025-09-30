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
