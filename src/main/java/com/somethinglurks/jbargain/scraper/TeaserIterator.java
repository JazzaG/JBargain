package com.somethinglurks.jbargain.scraper;

import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperCompetitionTeaser;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperDealTeaser;
import com.somethinglurks.jbargain.scraper.node.teaser.ScraperForumTeaser;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class TeaserIterator implements Iterator<Teaser> {

    private static final String DEAL_SELECTOR = "div.node-ozbdeal[id^=node]";
    private static final String FORUM_SELECTOR = "div#forum tbody tr";
    private static final String COMP_SELECTOR = "div.node-competition[id^=node]";

    private Tag tag;
    private ScraperUser user;

    private int currentPage = 0;
    private Elements teasers;
    private Factory factory;
    private int teaserIndex = 0;

    public TeaserIterator(Tag tag, ScraperUser user) {
        this.tag = tag;
        this.user = user;

        // Fetch first page
        fetchNextPage();
    }

    private boolean fetchNextPage() {
        try {
            String host = String.format("%s%s?page=%d", ScraperJBargain.HOST, tag.getEndpoint(), currentPage++);
            Connection connection = Jsoup.connect(host);

            // Add user cookies if authenticated
            if (user != null) {
                connection.cookies(user.getCookies());
            }

            // Create teaser elements based on type of teasers found
            Element pageElement = connection.get();
            if ((teasers = pageElement.select(DEAL_SELECTOR)).size() > 0) {
                factory = new DealFactory();
            } else if ((teasers = pageElement.select(FORUM_SELECTOR)).size() > 0) {
                factory = new ForumFactory();
            } else if ((teasers = pageElement.select(COMP_SELECTOR)).size() > 0) {
                factory = new CompetitionFactory();
            } else {
                return false;
            }

            // Reset teaser index
            teaserIndex = 0;
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean hasNext() {
        // Either there is another teaser on the page, or the next page exists and has teasers
        if (teasers != null && teaserIndex < teasers.size()) {
            return true;
        } else {
            return fetchNextPage();
        }
    }

    @Override
    public Teaser next() {
        return factory.create(teasers.get(teaserIndex++), user);
    }

    private interface Factory {
        Teaser create(Element element, ScraperUser user);
    }

    private class DealFactory implements Factory {
        @Override
        public Teaser create(Element element, ScraperUser user) {
            return new ScraperDealTeaser(element, user);
        }
    }

    private class CompetitionFactory implements Factory {
        @Override
        public Teaser create(Element element, ScraperUser user) {
            return new ScraperCompetitionTeaser(element, user);
        }
    }

    private class ForumFactory implements Factory {
        @Override
        public Teaser create(Element element, ScraperUser user) {
            // We are within the category if the parent table has class "forum-topics"
            boolean withinCategory = element
                    .parent() // <tbody>
                    .parent() // <table>
                    .hasClass("forum-topics");

            return new ScraperForumTeaser(element, user, withinCategory);
        }
    }

}
