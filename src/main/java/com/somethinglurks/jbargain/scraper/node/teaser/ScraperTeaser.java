package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.nodes.Element;

public abstract class ScraperTeaser implements Teaser {

    protected Element element;
    protected ScraperUser user;

    ScraperTeaser(Element element, ScraperUser user) {
        this.element = element;
        this.user = user;
    }

}
