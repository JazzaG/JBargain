package com.somethinglurks.jbargain.scraper.user;

import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.ReplyBuilder;
import com.somethinglurks.jbargain.api.user.User;
import com.somethinglurks.jbargain.api.user.exception.VoteException;
import com.somethinglurks.jbargain.api.user.notification.Notification;
import com.somethinglurks.jbargain.scraper.OzBargainApi;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
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
        Element element = Jsoup.connect(ScraperJBargain.HOST + "/user")
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
    public ReplyBuilder replyTo(Post post) {
        return new ScraperReplyBuilder(this, post);
    }

    @Override
    public ReplyBuilder replyTo(Comment comment) {
        return new ScraperReplyBuilder(this, comment);
    }

    @Override
    public void vote(Post post, Vote vote) throws VoteException {
        // Don't allow Vote.REVOKED
        if (vote == Vote.REVOKED) {
            throw new IllegalArgumentException("Cannot cast a revoke vote");
        }

        // Cast vote
        JSONObject result = OzBargainApi.call(this, "node_vote", post.getId(),
                vote.value + "");

        // Throw exception if there is an error message
        try {
            // If there is no result, pass on the error fault string
            if (result.get("result").toString().equals("null")) {
                throw new VoteException(result.getJSONObject("error").getString("faultString"));
            } else {
                throw new VoteException(result.getJSONObject("result").getString("errmsg"));
            }
        } catch (JSONException ignored) {

        }
    }

    @Override
    public void vote(Comment comment, Vote vote) throws VoteException {
        // Don't allow Vote.REVOKED
        if (vote == Vote.REVOKED) {
            throw new IllegalArgumentException("Cannot cast a revoke vote");
        }

        // Cast vote
        JSONObject result = OzBargainApi.call(this, "comment_vote", comment.getId(),
                vote.value + "");

        // Throw exception if there is an error message
        try {
            throw new VoteException(result.getJSONObject("result").getString("errmsg"));
        } catch (JSONException ignored) {

        }
    }

    @Override
    public void revokeVote(Post post) throws VoteException {

    }

    @Override
    public void revokeVote(Comment comment) throws VoteException {
        JSONObject result = OzBargainApi.call(this,
                "comment_revoke_vote", comment.getId(), this.getId());

        // Throw exception if there is an error message
        try {
            throw new VoteException(result.getJSONObject("result").getString("errmsg"));
        } catch (JSONException ignored) {

        }
    }
}
