package edu.bsu.cs;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            String articleTitle = getArticleTitle();

            if (articleTitle == null || articleTitle.isBlank()) {
                System.out.println("Error: No page requested.");
                return;
            }

            URLConnection connection = GetJsonData.connectToWikipedia(articleTitle);
            String rawJsonData = GetJsonData.readJsonAsStringFrom(connection);

            if (Format.hasNoPage(rawJsonData)) {
                System.out.println("Error: No page found for title '" + articleTitle + "'");
                return;
            }

            Format.printFormattedJson(rawJsonData);

        }  catch (IOException e) {
            System.out.println("Error: Network issue while trying to reach Wikipedia.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

    }

    public static String getArticleTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter article title:");
        return scanner.nextLine();
    }
}