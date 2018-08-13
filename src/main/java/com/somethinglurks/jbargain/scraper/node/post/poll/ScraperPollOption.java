package com.somethinglurks.jbargain.scraper.node.post.poll;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
import com.somethinglurks.jbargain.scraper.node.meta.AuthorElementAdapter;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;

public class ScraperPollOption implements PollOption {

    private Element element;
    private String nodeId;

    private Author author;
    private Date date;

    public ScraperPollOption(Element element, String nodeId) {
        this.element = element;
        this.nodeId = nodeId;

        // Set author and date if item was suggested by another user
        if (element.select("div.suggest").size() == 1) {
            author = new AuthorElementAdapter(element.selectFirst("div.suggest"));

            date = StringToDate.parsePostDate(element.select("div.suggest a").text(), true);
        } else {
            author = null;
            date = null;
        }
    }

    @Override
    public String getNodeId() {
        return nodeId;
    }

    @Override
    public String getDescription() {
        return element.select("span.polltext").text();
    }

    @Override
    public Author getAuthor() {
        return author;
    }

    @Override
    public Date getPostDate() {
        return date;
    }

    @Override
    public int getScore() {
        return Integer.parseInt(element.select("div.n-vote > span > span").text());
    }

    @Override
    public Vote getUserVote() {
        if (element.select("div.n-vote").hasClass("voteup")) {
            return Vote.POSITIVE;
        } else {
            return null;
        }
    }

    @Override
    public String getId() {
        return element.attr("data-oid");
    }
}
