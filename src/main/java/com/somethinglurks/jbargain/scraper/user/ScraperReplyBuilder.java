package com.somethinglurks.jbargain.scraper.user;

import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.ReplyBuilder;
import com.somethinglurks.jbargain.api.user.exception.ReplyException;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import com.somethinglurks.jbargain.scraper.node.post.ScraperPost;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class ScraperReplyBuilder implements ReplyBuilder {

    private ScraperUser user;

    private Post post;
    private Comment comment;

    private String content;
    private String pollSuggestion;

    private boolean isAssociated = false;

    public ScraperReplyBuilder(ScraperUser user, Post post) {
        this.user = user;
        this.post = post;
    }

    public ScraperReplyBuilder(ScraperUser user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }

    private void sendReply(String formAction, String formToken) throws ReplyException {
        try {
            // Submit reply
            Connection connection = Jsoup
                    .connect(ScraperJBargain.HOST + formAction)
                    .cookies(user.getCookies())
                    .method(Connection.Method.POST)
                    .data("edit[comment]", content)
                    .data("edit[rep_flag]", isAssociated ? "1" : "0")
                    .data("edit[form_token]", formToken)
                    .data("edit[form_id]", "comment_form");

            // Add poll suggestion if present
            if (pollSuggestion != null) {
                connection.data("edit[suggest]", pollSuggestion);
            }

            // Throw exception if error message is found
            Element errorElement;
            if ((errorElement = connection.execute().parse().selectFirst("div#content div.messages.error")) != null) {
                throw new ReplyException(errorElement.text());
            }
        } catch (IOException e) {
            throw new ReplyException(e.getMessage());
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
    public void reply() throws ReplyException {
        String formAction;
        String formToken;

        // Determine how to reply to the content
        if (post != null) {
            formAction = ((ScraperPost) post).getElement().select("form#comment_form").attr("action");
            formToken = ((ScraperPost) post).getElement().select("input#edit-form_token").val();
        } else if (comment != null) {
            try {
                // Load reply form to get token
                Element commentFormResponse = Jsoup
                        .connect(ScraperJBargain.HOST + "/ozbapi/comment/" + comment.getId() + "/replyform")
                        .cookies(this.user.getCookies())
                        .method(Connection.Method.GET)
                        .execute()
                        .parse();

                formAction = commentFormResponse.select("form").attr("action");
                formToken = commentFormResponse.select("input#edit-form_token").val();
            } catch (IOException ex) {
                throw new ReplyException(ex.getMessage());
            }
        } else {
            throw new NullPointerException("Target to reply to was null");
        }

        // Submit reply
        sendReply(formAction, formToken);
    }
}
