package com.somethinglurks.jbargain.scraper.user;

import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.User;
import com.somethinglurks.jbargain.api.user.notification.Notification;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ScraperUser implements User {

    private String id;
    private String username;
    private String avatarUrl;

    private Map<String, String> cookies;

    public ScraperUser(Map<String, String> cookies) throws IOException {
        this.cookies = cookies;

        // Load user details
        Element element = Jsoup.connect("https://ozbargain.com.au/user")
                .cookies(this.cookies)
                .method(Connection.Method.GET)
                .execute()
                .parse();

        this.id = element.baseUri().replaceAll("[^0-9]", "");
        this.username = element.select("title").text().replaceAll(" (.+)", "");
        this.avatarUrl = element.select("div.userinfo img.gravatar").attr("src");
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public List<Notification> getNotifications() {
        return null;
    }

    @Override
    public boolean replyTo(Post post, String reply, boolean associated) {
        return false;
    }

    @Override
    public boolean replyTo(Comment comment, String reply, boolean associated) {
        try {
            // Load reply form
            Connection connection = Jsoup
                    .connect("https://ozbargain.com.au/ozbapi/comment/" + comment.getId() + "/replyform")
                    .cookies(this.cookies)
                    .method(Connection.Method.GET);

            Connection.Response response = connection.execute();

            // Fetch token from form
            String token = response.parse().select("input#edit-form_token").val();

            // Fetch endpoint from form
            String endpoint = response.parse().select("form").attr("action");

            // Submit form
            connection
                    .data("edit[comment]", reply)
                    .data("edit[rep_flag]", associated ? "1" : "0")
                    .data("edit[form_token]", token)
                    .data("edit[form_id]", "comment_form")
                    .data("op", "Post comment")
                    .method(Connection.Method.POST)
                    .url("https://ozbargain.com.au" + endpoint)
                    .execute();

            // Validate
            // TODO:
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean vote(Post post, boolean up) {
        try {
            String data = String.format("{ \"version\": \"1.0\", \"method\": \"node_vote\", \"params\": [%s, %s, false] }",
                    post.getId(),
                    up ? "1" : "-1");

            Connection.Response response = Jsoup.connect("https://www.ozbargain.com.au/api/rpc")
                    .requestBody(data)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .followRedirects(false)
                    .ignoreContentType(true)
                    .cookies(this.cookies)
                    .method(Connection.Method.POST)
                    .execute();

            // Successful vote response should show the vote count and a null error
            String result = response.body().replaceAll("\\{\"result\": \\{\"neg\": \\d+, \"pos\": \\d+}, \"error\": null}", "");
            return result.isEmpty();

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean vote(Comment comment, boolean up) {
        return false;
    }
}
