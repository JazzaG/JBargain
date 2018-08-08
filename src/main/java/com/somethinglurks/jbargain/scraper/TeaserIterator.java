package com.somethinglurks.jbargain.scraper;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeaserIterator implements Iterator<List<Teaser>> {

    private String endpoint;
    private ScraperUser user;

    private Element currentPageElement;
    private int currentPageIndex = 0;
    private boolean firstRun = true;

    public TeaserIterator(String endpoint, ScraperUser user) {
        this.endpoint = endpoint;
        this.user = user;
    }

    private void loadPage() {
        firstRun = false;

        try {
            String host = ScraperJBargain.HOST + endpoint + "?page=" + currentPageIndex++;
            Connection connection = Jsoup.connect(host);

            if (user != null) {
                connection.cookies(user.getCookies());
            }

            currentPageElement = connection.get();
        } catch (IOException ignored) {
            // TODO: something here
        }
    }

    @Override
    public boolean hasNext() {
        // Check if current page element can go to next page, or if this is the first run
        return firstRun || (currentPageElement != null
                && currentPageElement.select("a.pager-next.active > i.fa.fa-chevron-right").size() == 1);
    }

    @Override
    public List<Teaser> next() {
        loadPage();
        List<Teaser> teasers = new ArrayList<>();

        if (currentPageElement != null) {
            // Determine type
            Elements elements;

            // Deals
            if ((elements = currentPageElement.select("div.node-ozbdeal[class^=node]")).size() > 0) {
                for (Element element : elements) {
                    teasers.add(new ScraperDealTeaser(element));
                }
            }

            // Forum
            if ((elements = currentPageElement.select("table.forum-topics tbody tr")).size() > 0) {
                for (Element element : elements) {
                    teasers.add(new ScraperForumTeaser(element));
                }
            }

            // Competitions
            if ((elements = currentPageElement.select("div.node-competition[class^=node]")).size() > 0) {
                for (Element element : elements) {
                    teasers.add(new ScraperCompetitionTeaser(element));
                }
            }
        }

        return teasers;
    }
}
