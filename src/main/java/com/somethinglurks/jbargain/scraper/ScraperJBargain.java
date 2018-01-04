package com.somethinglurks.jbargain.scraper;

import com.somethinglurks.jbargain.api.JBargain;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.api.user.AuthenticationException;
import com.somethinglurks.jbargain.api.user.User;
import com.somethinglurks.jbargain.scraper.node.post.ScraperCompetitionPost;
import com.somethinglurks.jbargain.scraper.node.post.ScraperDealPost;
import com.somethinglurks.jbargain.scraper.node.post.ScraperForumPost;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperCompetitionTeaser;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperDealTeaser;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperForumTeaser;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScraperJBargain implements JBargain {

    private static final String HOST = "https://ozbargain.com.au";

    @Override
    public User authenticateUser(String username, String password) throws AuthenticationException {
        try {
            Connection connection = Jsoup.connect(HOST + "/user/login?destination=front");

            // Make initial connection to get cookies and token
            String token = connection.get()
                    .select("input#edit-form_token")
                    .val();

            // Send login request
            Connection.Response login = connection
                    .data("edit[name]", username)
                    .data("edit[pass]", password)
                    .data("edit[form_token]", token)
                    .data("edit[form_id]", "user_login")
                    .data("op", "Log in")
                    .method(Connection.Method.POST)
                    .cookies(connection.response().cookies())
                    .execute();

            // Verify that there is no error message
            String text = login.parse().select("div.messages.error").text();

            if (text.contains("Unrecognised username or password")) {
                throw new AuthenticationException("Invalid credentials");
            } else if (!text.isEmpty()) {
                throw new AuthenticationException(text);
            } else {
                return new ScraperUser(login.cookies());
            }

        } catch (IOException ignored) {
            throw new AuthenticationException(ignored.getMessage());
        }
    }

    @Override
    public Post getPostById(String id, User user) throws IOException {
        // Download the page
        Element element = Jsoup.connect(HOST + "/node/" + id).get();

        /* Determine what type of node this is */
        Element post = element.select("div.node").first();

        // Deal node
        if (post.hasClass("node-ozbdeal")) {
            return new ScraperDealPost(element, (ScraperUser) user);
        }

        // Forum node
        if (post.hasClass("node-forum")) {
            return new ScraperForumPost(element, (ScraperUser) user);
        }

        // Competition node
        if (post.hasClass("node-competition")) {
            return new ScraperCompetitionPost(element, (ScraperUser) user);
        }

        throw new IllegalArgumentException("No such node with id " + id);
    }

    @Override
    public List<Teaser> getFeedByTag(Tag tag, User user, int page) throws IOException {
        Connection connection = Jsoup.connect(HOST + tag.getEndpoint() + "?page=" + page);

        // Append user cookies if logged in
        if (user != null) {
            connection.cookies(((ScraperUser) user).getCookies());
        }

        return getFeed(connection.get());
    }

    private List<Teaser> getFeed(Element pageElement) {
        List<Teaser> nodes = new ArrayList<>();

        // Determine type
        Elements elements;

        // Deals
        if ((elements = pageElement.select("div.node-ozbdeal[class^=node]")).size() > 0) {
            for (Element element : elements) {
                nodes.add(new ScraperDealTeaser(element));
            }
        }

        // Forum
        if ((elements = pageElement.select("table.forum-topics tbody tr")).size() > 0) {
            for (Element element : elements) {
                nodes.add(new ScraperForumTeaser(element));
            }
        }

        // Competitions
        if ((elements = pageElement.select("div.node-competition[class^=node]")).size() > 0) {
            for (Element element : elements) {
                nodes.add(new ScraperCompetitionTeaser(element));
            }
        }

        return nodes;
    }

}
