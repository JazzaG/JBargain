package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.DealPost;
import com.somethinglurks.jbargain.scraper.node.DealDateWrapper;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperDealPost extends ScraperPost implements DealPost {

    private DealDateWrapper dateWrapper;

    public ScraperDealPost(Element element, ScraperUser user) {
        super(element, user);
        this.dateWrapper = new DealDateWrapper(element);
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
        return dateWrapper.getStartDate();
    }

    @Override
    public Date getEndDate() {
        return dateWrapper.getEndDate();
    }

    @Override
    public String getWebsite() {
        return element.select("span.via a").text();
    }

    @Override
    public String getThumbnailUrl() {
        return element.select("div.foxshot-container img").attr("src");
    }

    @Override
    public int getNumberOfClicks() {
        return StringToInteger.parseSelector(element, "span.via span.nodeclicks");
    }

    @Override
    public int getPositiveVotes() {
        return StringToInteger.parseSelector(element, "div.n-vote span.voteup");
    }

    @Override
    public int getNegativeVotes() {
        return StringToInteger.parseSelector(element, "div.n-vote span.votedown");
    }

    @Override
    public List<Voter> getVoters() {
        return null; // TODO
    }

    @Override
    public Vote getUserVote() {
        return null; // TODO
    }
}
