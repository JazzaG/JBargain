package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.DealNode;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.DealPost;
import com.somethinglurks.jbargain.scraper.node.ScraperDealNode;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperDealPost extends ScraperPost implements DealPost {

    private DealNode dealNode;

    public ScraperDealPost(Element element, ScraperUser user) {
        super(element, user);
        this.dealNode = new ScraperDealNode(element, getId(), user);
    }

    @Override
    public List<Tag> getTags() {
        List<Tag> tags = new ArrayList<>();

        for (Element span : element.select("div.taxonomy span:not(:first-child)")) {
            tags.add(new Tag(
                    span.text(),
                    span.select("a").attr("href")
            ));
        }

        return tags;
    }

    @Override
    public Date getStartDate() {
        return dealNode.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return dealNode.getEndDate();
    }

    @Override
    public String getWebsite() {
        return dealNode.getWebsite();
    }

    @Override
    public String getThumbnailUrl() {
        return dealNode.getThumbnailUrl();
    }

    @Override
    public int getNumberOfClicks() {
        return StringToInteger.parseSelector(element, "span.via span.nodeclicks");
    }

    @Override
    public int getPositiveVotes() {
        return dealNode.getPositiveVotes();
    }

    @Override
    public int getNegativeVotes() {
        return dealNode.getNegativeVotes();
    }

    @Override
    public List<Voter> getVoters() {
        return dealNode.getVoters();
    }

    @Override
    public Vote getUserVote() {
        return dealNode.getUserVote();
    }
}
