package com.somethinglurks.jbargain.scraper.node;

import com.somethinglurks.jbargain.api.node.CompetitionNode;
import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperCompetitionNode extends ScraperNode implements CompetitionNode {

    public ScraperCompetitionNode(Element element, String id, ScraperUser user) {
        super(element, id, user);
    }

    @Override
    public int getPositiveVotes() {
        return 0;
    }

    @Override
    public Date getDrawDate() {
        return null;
    }

    @Override
    public String getPrizePool() {
        return null;
    }

    @Override
    public String getOpenTo() {
        return null;
    }

    @Override
    public String getMethods() {
        return null;
    }

    @Override
    public String getRequirements() {
        return null;
    }

    @Override
    public int getNumberOfEntrants() {
        return 0;
    }

    @Override
    public int getNumberOfWinners() {
        return 0;
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
    public Vote getUserVote() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getWebsite() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return null;
    }

    @Override
    public List<Voter> getVoters() {
        return null;
    }
}
