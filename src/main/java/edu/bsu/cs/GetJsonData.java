package edu.bsu.cs;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class GetJsonData {

    public static URLConnection connectToWikipedia(String articleTitle) throws IOException {
        String encodedTitle = URLEncoder.encode(articleTitle, StandardCharsets.UTF_8);
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&format=json" +
                "&prop=revisions&titles=" + encodedTitle +
                "&rvprop=timestamp|user&rvlimit=16&redirects";

        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent",
                "FirstProject/0.1 (academic use; https://example.com)");
        connection.connect();
        return connection;
    }

    public static String readJsonAsStringFrom(URLConnection connection) throws IOException {
        return new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }



}