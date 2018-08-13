package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.api.node.teaser.CompetitionTeaser;
import com.somethinglurks.jbargain.scraper.node.meta.AuthorElementAdapter;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.node.meta.NodeVotersList;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperCompetitionTeaser extends ScraperTeaser implements CompetitionTeaser {

    public ScraperCompetitionTeaser(Element element, ScraperUser user) {
        super(element, user);
    }

    @Override
    public String getId() {
        return element.id().replaceAll("[^0-9]", "");
    }

    @Override
    public String getTitle() {
        return element.select("h2.title a").text();
    }

    @Override
    public Author getAuthor() {
        return new AuthorElementAdapter(element,
                "div.submitted strong a",
                "div.n-left img.gravatar",
                "div.submitted span");
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("div.submitted").text(), true);
    }

    @Override
    public Tag getCategory() {
        return new Tag(
                element.select("span.tag a").text(),
                element.select("span.tag a").attr("href")
        );
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("h2.title span"));
    }

    @Override
    public Date getDrawDate() {
        Date[] dates = StringToDate.parseDealDate(element.select("span.nodeexpiry").text());

        return dates[0];
    }

    @Override
    public String getPrizePool() {
        return element.select("span[title=\"Total prize pool\"]").text().replace("Prize pool ", "");
    }

    @Override
    public String getOpenTo() {
        return element.select("span[title=\"Open to\"]").text();
    }

    @Override
    public String getMethods() {
        return element.select("span[title=\"Entry methods\"]").text();
    }

    @Override
    public String getRequirements() {
        return element.select("span[title=\"Entry requirements\"]").text();
    }

    @Override
    public int getNumberOfComments() {
        return StringToInteger.parseSelector(element, "ul.links li:nth-child(1)");
    }

    @Override
    public int getNumberOfEntrants() {
        return StringToInteger.parseSelector(element, "ul.links li:nth-child(2)");
    }

    @Override
    public int getNumberOfWinners() {
        return StringToInteger.parseSelector(element, "ul.links li:nth-child(3)");
    }

    @Override
    public String getWebsite() {
        return element.select("span.via a").attr("href");
    }

    @Override
    public String getThumbnailUrl() {
        return element.select("div.foxshot-container img").attr("src");
    }

    @Override
    public int getPositiveVotes() {
        return StringToInteger.parseSelector(element, "span.nvb.voteup");
    }

    @Override
    public List<Voter> getVoters() {
        if (user == null) {
            return null;
        }

        return new NodeVotersList(getId(), user);
    }

    @Override
    public Vote getUserVote() {
        if (element.select("div.n-vote.voteup").size() == 1) {
            return Vote.POSITIVE;
        } else {
            return null;
        }
    }

}
