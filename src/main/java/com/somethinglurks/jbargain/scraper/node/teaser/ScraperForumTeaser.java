package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.teaser.ForumTeaser;
import com.somethinglurks.jbargain.scraper.node.meta.AuthorElementAdapter;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperForumTeaser extends ScraperTeaser implements ForumTeaser {

    private boolean withinCategory;
    private Tag category;

    public ScraperForumTeaser(Element element, ScraperUser user, boolean withinCategory, Tag category) {
        super(element, user);

        this.withinCategory = withinCategory;
        this.category = category;
    }

    @Override
    public Author getLastReplyAuthor() {
        if (withinCategory) {
            return new AuthorElementAdapter(element.selectFirst("td.last-reply"));
        } else {
            return null;
        }
    }

    @Override
    public Date getLastReplyDate() {
        if (withinCategory) {
            return StringToDate.parsePostDate(element.select("td.last-reply div").text(), false);
        } else {
            return null;
        }
    }

    @Override
    public String getId() {
        return element
                .select("td.topic span.title a")
                .attr("href")
                .replaceAll("[^0-9]", "");
    }

    @Override
    public String getTitle() {
        return element.select("td.topic span.title").text();
    }

    @Override
    public Author getAuthor() {
        return new AuthorElementAdapter(element.selectFirst("td.created"));
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("td.created div").text(), false);
    }

    @Override
    public Tag getCategory() {
        if (withinCategory) {
            return category;
        } else {
            return new Tag(
                    element.select("td.forum a").text(),
                    element.select("td.forum a").attr("href"));
        }
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("td.topic i"));
    }

    @Override
    public int getNumberOfComments() {
        if (element.selectFirst("td.replies") == null) {
            return 0;
        } else {
            return StringToInteger.parseSelector(element, "td.replies");
        }
    }
}
