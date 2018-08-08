package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentElementIterator implements Iterator<List<Comment>> {

    private ScraperUser user;
    private String id;
    private Element element;

    private int totalPages;
    private int currentPage = 0;

    public CommentElementIterator(ScraperUser user, String id, Element element) {
        this.user = user;
        this.id = id;

        // Find total pages
        Matcher matcher = Pattern.compile("(\\d+)(?!.*\\d)").matcher(element.select("ul.pager").text());
        if (matcher.find()) {
            totalPages = Integer.parseInt(matcher.group(1));
        } else {
            totalPages = 1;
        }
    }

    private void fetchComments() {
        try {
            if (user != null) {
                element = Jsoup.connect("https://ozbargain.com.au/node/" + id + "/comments")
                        .cookies(user.getCookies())
                        .get();
            } else {
                element = Jsoup.connect("https://ozbargain.com.au/node/" + id + "?page=" + currentPage++).get();
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public boolean hasNext() {
        if (user != null) {
            return element == null;
        } else {
            return currentPage < totalPages;
        }
    }

    @Override
    public List<Comment> next() {
        fetchComments();
        List<Comment> comments = new ArrayList<>();

        // Find comments
        if (element != null) {
            String value;
            int level;
            for (Element item : element.select("ul.comment li")) {
                // Ignore empty items
                if (item.children().size() == 0) {
                    continue;
                }

                // Determine level of comment
                value = item.parent().className().replaceAll("[^0-9]", "");
                level = Integer.parseInt(value);

                // Add comment
                comments.add(new CommentElementAdapter(item, level));
            }
        }

        return comments;
    }
}
