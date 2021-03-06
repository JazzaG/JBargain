package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentIterator implements Iterator<Comment> {

    private String postId;
    private Element postElement;
    private ScraperUser user;

    private int totalPages;
    private int currentPage = 0;

    private Elements comments;
    private int commentIndex = 0;

    public CommentIterator(String postId, Element postElement, ScraperUser user) {
        this.postId = postId;
        this.postElement = postElement;
        this.user = user;

        // Find total pages
        Matcher matcher = Pattern.compile("(\\d+)(?!.*\\d)").matcher(postElement.select("ul.pager").text());
        if (matcher.find()) {
            totalPages = Integer.parseInt(matcher.group(1));
        } else {
            totalPages = 1;
        }

        // Find comments on first page
        fetchComments();
    }

    private void fetchNextPage() {
        try {
            String host = String.format("%s/node/%s?page=%d", ScraperJBargain.HOST, postId, ++currentPage);
            Connection connection = Jsoup.connect(host);

            // Add user cookies if exists
            if (user != null) {
                connection.cookies(user.getCookies());
            }

            postElement = connection.get();
            fetchComments();
        } catch (IOException ignored) {

        }
    }

    private void fetchComments() {
        comments = postElement.select("div.comment-wrap");
        commentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        // Either there is another comment-element, or there is another page
        if (commentIndex < comments.size()) {
            return true;
        } else {
            if (currentPage < totalPages - 1) {
                fetchNextPage();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public Comment next() {
        // Load next comment
        Element commentWrap = comments.get(commentIndex++);

        // Get comment id
        String commentId = commentWrap.id().replaceAll("[^0-9]", "");

        // Get level of comment
        String levelValue = commentWrap.parent().parent().className().replaceAll("[^0-9]", "");
        int level = Integer.parseInt(levelValue);

        // Get type of comment
        Element targetElement;
        Comment.Type type;
        if ((targetElement = commentWrap.selectFirst("div.unpublished")) != null) {
            type = Comment.Type.UNPUBLISHED;
        } else if ((targetElement = commentWrap.selectFirst("div.hidden")) != null) {
            type = Comment.Type.HIDDEN;
        } else {
            targetElement = commentWrap.selectFirst("div.comment");
            type = Comment.Type.VISIBLE;
        }

        return new ScraperComment(targetElement, commentId, level, type);
    }
}
