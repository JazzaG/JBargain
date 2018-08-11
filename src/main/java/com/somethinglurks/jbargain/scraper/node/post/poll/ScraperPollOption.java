package com.somethinglurks.jbargain.scraper.node.post.poll;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            author = new Author(
                    element.select("div.suggest strong a").attr("href").replaceAll("[^0-9]", ""),
                    element.select("div.suggest strong a").text(),
                    "",
                    new ArrayList<>()
            );

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
    public String getOption() {
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
    public int getPositiveVotes() {
        return getScore();
    }

    @Override
    public int getNegativeVotes() {
        return 0; // poll options cannot be negatively voted on
    }

    @Override
    public List<Voter> getVoters() {
        return null; // cannot view voters of poll options
    }

    @Override
    public Vote getUserVote() {
        return null; // TODO
    }

    @Override
    public String getId() {
        return element.attr("data-oid");
    }
}
