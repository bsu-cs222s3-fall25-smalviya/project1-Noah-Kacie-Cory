package edu.bsu.cs;

import org.json.JSONArray;
import org.json.JSONObject;

public class Format {

   public static void printFormattedJson(String rawJsonData) {
        JSONObject root = new JSONObject(rawJsonData);
        JSONObject query = root.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");

        String firstKey = pages.keys().next();
        JSONObject page = pages.getJSONObject(firstKey);

        if (!page.has("revisions")) {
           System.out.println("No revisions available.");
           return;
        }
        JSONArray revisions = page.getJSONArray("revisions");

        for (int i = 0; i < revisions.length(); i++) {
            JSONObject rev = revisions.getJSONObject(i);
            String timestamp = rev.getString("timestamp");
            String user = rev.getString("user");
            System.out.printf("%d  %s  %s%n", i + 1, timestamp, user);
        }
    }

    public static boolean hasNoPage(String rawJsonData) {
        JSONObject root = new JSONObject(rawJsonData);
        JSONObject query = root.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");

        String firstKey = pages.keys().next();
        return firstKey.equals("-1");
    }
}
