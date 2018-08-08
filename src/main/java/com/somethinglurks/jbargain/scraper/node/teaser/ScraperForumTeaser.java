package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.teaser.ForumTeaser;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperForumTeaser extends ScraperTeaser implements ForumTeaser {

    public ScraperForumTeaser(Element element) {
        super(element);
    }

    @Override
    public Author getLastReplyAuthor() {
        return new Author(
                element.select("td.last-reply a").attr("href").replaceAll("[^0-9]", ""),
                element.select("td.last-reply a").text(),
                element.select("td.last-reply img.usericon").attr("src"),
                new ArrayList<>()
        );
    }

    @Override
    public Date getLastReplyDate() {
        return StringToDate.parsePostDate(element.select("td.last-reply div").text(), false);
    }

    @Override
    public String getId() {
        return element.select("td.topic span.title a").attr("href").replaceAll("[^0-9]", "");
    }

    @Override
    public String getTitle() {
        return element.select("td.topic span.title").text();
    }

    @Override
    public Author getAuthor() {
        return new Author(
                element.select("td.created a").attr("href").replaceAll("[^0-9]", ""),
                element.select("td.created a").text(),
                element.select("td.created img.usericon").attr("src"),
                new ArrayList<>()
        );
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("td.created div").text(), false);
    }

    @Override
    public Tag getCategory() {
        // TODO:
        return null;
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("td.topic i"));
    }

    @Override
    public int getNumberOfComments() {
        return StringToInteger.parseSelector(element, "td.replies");
    }
}
