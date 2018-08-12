package com.somethinglurks.jbargain.scraper.node.teaser;

import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.api.node.teaser.CompetitionTeaser;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperCompetitionTeaser extends ScraperTeaser implements CompetitionTeaser {

    public ScraperCompetitionTeaser(Element element) {
        super(element);
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
        return new Author(
                element.select("div.submitted strong a").attr("href").replaceAll("[^0-9]", ""),
                element.select("div.submitted strong a").text(),
                element.select("div.n-left img.gravatar").attr("src"),
                Flags.createFromElements(element.select("div.submitted span"))
        );
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
    public int getNumberOfEntrants() {
        String value = element.select("ul.links li:nth-child(2)").text();

        return Integer.parseInt(value);
    }

    @Override
    public int getNumberOfWinners() {
        String value = element.select("ul.links li:nth-child(3)").text();

        return Integer.parseInt(value);
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
        String value = element.select("span.nvb.voteup").text();

        return Integer.parseInt(value);
    }

    @Override
    public List<Voter> getVoters() {
        return null; // TODO
    }

    @Override
    public Vote getUserVote() {
        return null; // TODO
    }

    @Override
    public int getNumberOfComments() {
        return 0; // TODO
    }
}
