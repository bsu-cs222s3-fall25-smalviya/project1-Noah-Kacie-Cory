package edu.bsu.cs;

public class Format {

   public static void printFormattedJson(String rawJsonData) {
        JSONObject root = new JSONObject(rawJsonData);
        JSONObject query = root.getJSONObject("query");
        JSONObject pages = query.getJSONObject("pages");

        // Get first page object
        String firstKey = pages.keys().next();
        JSONObject page = pages.getJSONObject(firstKey);
        JSONArray revisions = page.getJSONArray("revisions");

        // Print each revision
        for (int i = 0; i < revisions.length(); i++) {
            JSONObject rev = revisions.getJSONObject(i);
            String timestamp = rev.getString("timestamp");
            String user = rev.getString("user");
            System.out.printf("%d  %s  %s%n", i + 1, timestamp, user);
        }
    }
}
