package com.somethinglurks.jbargain.scraper.user;

import com.somethinglurks.jbargain.api.node.Node;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
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
    public void vote(Votable votable, Vote vote) throws VoteException {
        // Don't allow Vote.REVOKED
        if (vote == Vote.REVOKED) {
            throw new VoteException("Cannot cast a revoke vote. Use revokeVote() instead");
        }

        // Determine how to cast vote
        // This could use some fancy design pattern like visitor or strategy, or I could just keep it simple...
        String method;
        String[] params;

        if (votable instanceof Node) {
            method = "node_vote";
            params = new String[] { votable.getId(), vote.value + "" };
        } else if (votable instanceof Comment) {
            method = "comment_vote";
            params = new String[] { votable.getId(), vote.value + "" };
        } else if (votable instanceof PollOption) {
            method = "poll_vote";
            params = new String[] { votable.getId() };
        } else {
            throw new VoteException("Unknown votable type");
        }

        this.sendVoteCall(method, params);
    }

    @Override
    public void revokeVote(Votable votable) throws VoteException {
        // Determine how to revoke vote
        String method;
        String[] params;

        if (votable instanceof Node) {
            method = "node_revoke_vote";
            params = new String[] { votable.getId(), this.getId() };
        } else if (votable instanceof Comment) {
            method = "node_revoke_comment";
            params = new String[] { votable.getId(), this.getId() };
        } else if (votable instanceof PollOption) {
            method = "poll_revoke";
            params = new String[] { ((PollOption) votable).getNodeId() };
        } else {
            throw new VoteException("Unknown votable type");
        }

        this.sendVoteCall(method, params);

    }

    private void sendVoteCall(String method, String[] params) throws VoteException {
        JSONObject result = OzBargainApi.call(this, method, params);

        // Throw exception if there is an error message
        try {
            // If there is no result, pass on the error fault string
            if (result.get("result").toString().equals("null")) {
                throw new VoteException(result.getJSONObject("error").getString("faultString"));
            } else {
                throw new VoteException(result.getJSONObject("result").getString("errmsg"));
            }
        } catch (JSONException ignored) {

        } catch (Exception ex) {
            throw new VoteException(ex.getMessage());
        }
    }

}
