package com.somethinglurks.jbargain.scraper.user;

import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.ReplyBuilder;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ScraperReplyBuilder implements ReplyBuilder {

    private ScraperUser user;

    private Post post;
    private Comment comment;

    private String content;
    private String pollSuggestion;

    private boolean isAssociated;

    public ScraperReplyBuilder(ScraperUser user, Post post) {
        this.user = user;
        this.post = post;
    }

    public ScraperReplyBuilder(ScraperUser user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    private void replyToPost() {

    }

    private void replyToComment() {
        try {
            // Load reply form to get token
            Connection connection = Jsoup
                    .connect(ScraperJBargain.HOST + "/ozbapi/comment/" + comment.getId() + "/replyform")
                    .cookies(this.user.getCookies())
                    .method(Connection.Method.GET);

            Connection.Response formResponse = connection.execute();

            // Fetch token from form
            String token = formResponse.parse().select("input#edit-form_token").val();

            // Fetch endpoint from form
            String endpoint = formResponse.parse().select("form").attr("action");

            // Submit form
            Connection.Response submitResponse = connection
                    .data("edit[comment]", this.content)
                    .data("edit[rep_flag]", this.isAssociated ? "1" : "0")
                    .data("edit[form_token]", token)
                    .data("edit[form_id]", "comment_form")
                    .data("op", "Post comment")
                    .method(Connection.Method.POST)
                    .url(ScraperJBargain.HOST + endpoint)
                    .execute();

            // Validate
            // TODO:

        } catch (IOException ignored) {

        }
    }

    @Override
    public ReplyBuilder addContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public ReplyBuilder addPollSuggestion(String pollSuggestion) {
        this.pollSuggestion = pollSuggestion;
        return this;
    }

    @Override
    public ReplyBuilder setAssociated(boolean isAssociated) {
        this.isAssociated = isAssociated;
        return this;
    }

    @Override
    public void reply() {
        // Determine how to reply to the content
        if (post != null) {
            replyToPost();
        } else if (comment != null) {
            replyToComment();
        }
    }
}
