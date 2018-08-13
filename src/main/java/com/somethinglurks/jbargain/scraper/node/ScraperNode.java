package com.somethinglurks.jbargain.scraper.node;

import com.somethinglurks.jbargain.api.node.Node;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.nodes.Element;

public abstract class ScraperNode implements Node {

    protected Element element;
    protected String id;
    protected ScraperUser user;

    public ScraperNode(Element element, String id, ScraperUser user) {
        this.element = element;
        this.id = id;
        this.user = user;
    }

}
