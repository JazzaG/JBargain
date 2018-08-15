package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.DealNode;
import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.api.node.teaser.DealTeaser;
import com.somethinglurks.jbargain.scraper.node.ScraperDealNode;
import com.somethinglurks.jbargain.scraper.node.meta.FlagList;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;

public class ScraperDealTeaser extends ScraperTeaser implements DealTeaser {

    private DealNode dealNode;

    public ScraperDealTeaser(Element element, ScraperUser user) {
        super(element, user);
        this.dealNode = new ScraperDealNode(element, getId(), user);
    }

    @Override
    public String getId() {
        return element.id().replaceAll("[^0-9]", "");
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
    public String getTitle() {
        return element.select("h2.title a").text();
    }

    @Override
    public Author getAuthor() {
        return dealNode.getAuthor();
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("div.submitted").text(), true);
    }

    @Override
    public int getNumberOfComments() {
        // Remove the 'new' marker
        Elements repliesElement = element.clone().select("div.links ul li:nth-child(1)");
        repliesElement.select("span.marker").remove();

        return StringToInteger.parseSelector(repliesElement.first(), "*");
    }

    @Override
    public List<Flag> getFlags() {
        return new FlagList(element.select("h2.title span"));
    }

    @Override
    public Tag getCategory() {
        return new Tag(
                element.select("span.tag a").text(),
                element.select("span.tag a").attr("href")
        );
    }

    @Override
    public String getDescription() {
        return element.select("div.content p").text();
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

    @Override
    public boolean isFreebie() {
        return dealNode.isFreebie();
    }

    @Override
    public List<String> getCouponCodes() {
        return dealNode.getCouponCodes();
    }

}
