package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.CompetitionPost;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScraperCompetitionPost extends ScraperPost implements CompetitionPost {

    public ScraperCompetitionPost(Element element, ScraperUser user) {
        super(element, user);
    }

    private String getCompetitionSectionValue(String key) {
        Matcher matcher = Pattern.compile("<span .+?>" + key + "</span>[\\n\\s\\t]+<span .+?>(.+)</span>").matcher(element.text());

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    @Override
    public boolean hasMultipleDraws() {
        return getCompetitionSectionValue("Multi-draw").equalsIgnoreCase("yes");
    }

    @Override
    public String getPrizeDescription() {
        return getCompetitionSectionValue("Description");
    }

    @Override
    public String getTermsAndConditionsUrl() {
        return element.select("a[title=Link to terms and conditions]").attr("href");
    }

    @Override
    public Date getDrawDate() {
        return StringToDate.parseCompetitionDate(getCompetitionSectionValue("Draw Date"));
    }

    @Override
    public String getPrizePool() {
        return getCompetitionSectionValue("Total Prize Pool");
    }

    @Override
    public String getOpenTo() {
        return getCompetitionSectionValue("Open To");
    }

    @Override
    public String getMethods() {
        return getCompetitionSectionValue("Entry Methods");
    }

    @Override
    public String getRequirements() {
        return getCompetitionSectionValue("Prerequisites");
    }

    @Override
    public int getNumberOfEntrants() {
        return StringToInteger.parseSelector(element, "span#comp-entries-" + getId());
    }

    @Override
    public int getNumberOfWinners() {
        return StringToInteger.parseSelector(element, "span#comp-wins-" + getId());
    }

    @Override
    public String getWebsite() {
        return element.select("span.via a").text();
    }

    @Override
    public String getThumbnailUrl() {
        return element.select("div.foxshot-container img").attr("src");
    }

    @Override
    public int getNumberOfClicks() {
        return StringToInteger.parseSelector(element, "span.via span.nodeclicks");
    }

    @Override
    public int getPositiveVotes() {
        return StringToInteger.parseSelector(element, "div.n-vote span.voteup");
    }

    @Override
    public int getNegativeVotes() {
        return StringToInteger.parseSelector(element, "div.n-vote span.votedown");
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public List<Voter> getVoters() {
        return null;
    }

    @Override
    public Vote getUserVote() {
        return null;
    }
}
