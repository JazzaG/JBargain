package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import org.jsoup.nodes.Element;

public abstract class ScraperTeaser implements Teaser {

    protected Element element;

    ScraperTeaser(Element element) {
        this.element = element;
    }

}
