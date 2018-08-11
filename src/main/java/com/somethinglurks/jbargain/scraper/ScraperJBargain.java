package com.somethinglurks.jbargain.scraper;

import com.somethinglurks.jbargain.api.JBargain;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.api.user.User;
import com.somethinglurks.jbargain.api.user.exception.AuthenticationException;
import com.somethinglurks.jbargain.scraper.node.post.ScraperCompetitionPost;
import com.somethinglurks.jbargain.scraper.node.post.ScraperDealPost;
import com.somethinglurks.jbargain.scraper.node.post.ScraperForumPost;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;

public class ScraperJBargain implements JBargain {

    public static final String HOST = "https://www.ozbargain.com.au";

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

            // Return authenticated user object if no errors exist
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
        try {
            // Create connection
            Connection connection = Jsoup.connect(HOST + "/node/" + id);

            // Add user cookies
            if (user != null) {
                connection.cookies(((ScraperUser) user).getCookies());
            }

            // Download page
            Element element = connection.get();

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
        } catch (HttpStatusException ignored) {

        }

        throw new IllegalArgumentException("No such node with id " + id);
    }

    @Override
    public Post getPostById(String id) throws IOException {
        return getPostById(id, null);
    }

    @Override
    public Iterator<Teaser> getFeedByTag(Tag tag) throws IOException {
        return new TeaserIterator(tag.getEndpoint(), null);
    }

    @Override
    public Iterator<Teaser> getFeedByTag(Tag tag, User user) throws IOException {
        return new TeaserIterator(tag.getEndpoint(), (ScraperUser) user);
    }


}
