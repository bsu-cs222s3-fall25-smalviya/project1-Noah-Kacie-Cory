package edu.bsu.cs;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String articleTitle = getArticleTitle();
        URLConnection connection = GetJsonData.connectToWikipedia(articleTitle);
        String rawJsonData = GetJsonData.readJsonAsStringFrom(connection);
        Format.printRawJson(rawJsonData);
    }


    public static String getArticleTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter title below");
        return scanner.nextLine();
    }
}
