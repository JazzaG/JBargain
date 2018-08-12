package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.api.node.teaser.DealTeaser;
import com.somethinglurks.jbargain.scraper.node.DealDateWrapper;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperDealTeaser extends ScraperTeaser implements DealTeaser {

    private DealDateWrapper dateWrapper;

    public ScraperDealTeaser(Element element) {
        super(element);
        this.dateWrapper = new DealDateWrapper(element);
    }

    @Override
    public String getId() {
        return element.id().replaceAll("[^0-9]", "");
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
    public String getTitle() {
        return element.select("h2.title a").text();
    }

    @Override
    public Author getAuthor() {
        return new Author(
                element.select("div.submitted strong a").attr("href").replaceAll("[^0-9]", ""),
                element.select("div.submitted strong a").text(),
                element.select("div.n-left img.gravatar").attr("src"),
                Flags.createFromElements(element.select("div.submitted span"))
        );
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("div.submitted").text(), true);
    }

    @Override
    public int getNumberOfComments() {
        String value = element.select("div.links ul li:nth-child(1)").text();

        return Integer.parseInt(value);
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("h2.title span"));
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
        return element.select("span.via a").text();
    }

    @Override
    public String getThumbnailUrl() {
        return element.select("div.foxshot-conainer img").attr("src");
    }

    @Override
    public int getNumberOfClicks() {
        // Number of clicks isn't shown on the nodes, only within the posts
        return 0;
    }

    @Override
    public int getPositiveVotes() {
        String value = element.select("span.nvb.voteup").text();

        return Integer.parseInt(value);
    }

    @Override
    public int getNegativeVotes() {
        String value = element.select("span.nvb.votedown").text();

        return Integer.parseInt(value);
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
