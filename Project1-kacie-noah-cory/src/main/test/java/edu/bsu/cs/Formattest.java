package edu.bsu.cs;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class Formattest {

    // Verifies that hasNoPage() returns true when the page is missing
    @Test
    public void noPage_returnsTrue() {
        String json = """
            {
             "query": {
               "pages": {
                  "-1": {
                  "title": "NonExistentPage",
                   "missing": ""
                        }
                    }
                }
            }
            """;
        assertTrue(Format.hasNoPage(json));
    }

    // Verifies that hasNoPage() returns false when the page exists
    @Test
    public void pageExists_returnsFalse() {
        String json = """
            {
                "query": {
                  "pages": {
                    "123": {
                      "pageid": 123,
                        "title": "TestPage",
                        "revisions": [{
                           "user": "User",
                            "timestamp": "2023-01-01T00:00:00Z"
                      }]
                   }
                 }
              }
            }
            """;
        assertFalse(Format.hasNoPage(json));
    }

    // Verifies that printFormattedJson() correctly prints user and timestamp from revisions
    @Test
    public void printJson_withRevisions() {
        String json = """
            {
               "query": {
                "pages": {
                  "123": {
                    "pageid": 123,
                       "title": "TestPage",
                        "revisions": [
                          {"user": "User1", "timestamp": "2023-01-01T00:00:00Z"},
                            {"user": "User2", "timestamp": "2023-01-02T00:00:00Z"}
                    ]
                   }
                }
              }
            }
            """;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Format.printFormattedJson(json);

        String expected = """
            1  2023-01-01T00:00:00Z  User1
            2  2023-01-02T00:00:00Z  User2
            """;

        assertEquals(expected.trim(), out.toString().trim());
    }

    // Verifies that printFormattedJson() handles pages with no revisions
    @Test
    public void printJson_noRevisions() {
        String json = """
            {
                "query": {
                    "pages": {
                        "123": {
                            "pageid": 123,
                            "title": "TestPage"
                        }
                    }
                }
            }
            """;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Format.printFormattedJson(json);

        assertEquals("No revisions available.", out.toString().trim());
    }
}
