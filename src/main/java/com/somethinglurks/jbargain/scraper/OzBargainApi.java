package com.somethinglurks.jbargain.scraper;

import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class OzBargainApi {

    public static JSONObject call(ScraperUser user, String method, String... params) {
        /* Create parameter string */
        StringBuilder parameters = new StringBuilder();
        parameters.append("[");

        // Convert array into comma-separated string
        for (int i = 0; i < params.length; i++) {
            parameters.append(params[i]);

            if (i != params.length - 1) {
                parameters.append(", ");
            }
        }

        parameters.append("]");

        /* Create data string */
        String data = String.format("{ \"version\": \"1.0\", \"method\": \"%s\", \"params\": %s }",
                method, parameters.toString());

        /* Execute call */
        try {
            Connection.Response response = Jsoup.connect(ScraperJBargain.HOST + "/api/rpc")
                    .cookies(user.getCookies())
                    .requestBody(data)
                    .header("Content-Type", "application/json")
                    .header("Accept", "applicatin/json")
                    .followRedirects(false)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .execute();

            return new JSONObject(response.body());
        } catch (IOException ignored) {

        }

        return null;
    }

    public static Element showComment(String commentId) {
        String host = String.format("%s/ozbapi/comment/%s/content", ScraperJBargain.HOST, commentId);
        try {
            return Jsoup.connect(host).get();
        } catch (Exception ex) {
            return null;
        }
    }

}
