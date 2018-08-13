package com.somethinglurks.jbargain.scraper.node;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperForumNode extends ScraperNode implements ForumNode {

    public ScraperForumNode(Element element, String id, ScraperUser user) {
        super(element, id, user);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public Author getAuthor() {
        return null;
    }

    @Override
    public Date getPostDate() {
        return null;
    }

    @Override
    public Tag getCategory() {
        return null;
    }

    @Override
    public List<Flag> getFlags() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }
}
