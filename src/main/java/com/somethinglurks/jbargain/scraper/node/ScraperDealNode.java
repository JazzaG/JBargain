package com.somethinglurks.jbargain.scraper.node;

import com.somethinglurks.jbargain.api.node.DealNode;
import com.somethinglurks.jbargain.api.node.meta.*;
import com.somethinglurks.jbargain.scraper.node.meta.AuthorElementAdapter;
import com.somethinglurks.jbargain.scraper.node.meta.NodeVoterList;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperDealNode extends ScraperNode implements DealNode {

    private Date startDate;
    private Date endDate;

    public ScraperDealNode(Element element, String id, ScraperUser user) {
        super(element, id, user);
        loadDates();
    }

    private void loadDates() {
        String dateString = element.select("span.nodeexpiry").text();

        // Remove any prefixes
        dateString = dateString.replaceAll("^[^0-9]", "");

        // Remove marker text
        String markerText = element.select("span.nodeexpiry span.marker").text();
        dateString = dateString.replace(markerText, "").trim();

        // Parse dates
        Date[] dates = StringToDate.parseDealDate(dateString);

        // If the span containing the dates has an 'inactive' class, then the first date
        //   is the start date and second date is the end date
        // If the class does not exist, then the deal has already started and the first
        //   date is the end date

        if (element.select("span.nodeexpiry").hasClass("inactive")) {
            startDate = dates[0];
            endDate = dates[1];
        } else {
            endDate = dates[0];
        }
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
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String getTitle() {
        return element.select(".title:last-child").text();
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
    public String getWebsite() {
        return element.select("span.via a").text();
    }

    @Override
    public String getThumbnailUrl() {
        return element.select("div.foxshot-container img").attr("src");
    }

    @Override
    public Vote getUserVote() {
        if (element.select("div.n-vote.voteup").size() == 1) {
            return Vote.POSITIVE;
        }

        if (element.select("div.n-vote votedown").size() == 1) {
            return Vote.NEGATIVE;
        }

        return null;
    }

    @Override
    public List<Voter> getVoters() {
        if (user == null) {
            return null;
        }

        return new NodeVoterList(id, user);
    }

    @Override
    public boolean isFreebie() {
        return element.selectFirst("span.nodefreebie") != null;
    }

    @Override
    public List<String> getCouponCodes() {
        List<String> couponCodes = new ArrayList<>();

        for (Element codeElement : element.select("div.couponcode strong")) {
            couponCodes.add(codeElement.text());
        }

        return couponCodes;
    }

    @Override
    public Tag getCategory() {
        // Leave empty
        return null;
    }

    @Override
    public String getDescription() {
        // Leave empty
        return null;
    }

    @Override
    public List<Flag> getFlags() {
        // Leave empty
        return null;
    }

    @Override
    public String getId() {
        // Leave empty
        return null;
    }

}
