package com.somethinglurks.jbargain.scraper.node.meta;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class NodeVotersList extends ArrayList<Voter> {

    public NodeVotersList(String nodeId, ScraperUser user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try {
            Element votersPageElement = Jsoup
                    .connect(ScraperJBargain.HOST + "/node/" + nodeId + "/votes")
                    .cookies(user.getCookies())
                    .get();

            for (Element voteRow : votersPageElement.select("table#votes_table tr")) {
                add(new Voter(
                        createAuthor(voteRow),
                        determineVote(voteRow),
                        findDate(voteRow)
                ));
            }
        } catch (IOException ignored) {

        }
    }

    private Author createAuthor(Element voteRow) {
        return new Author(
                voteRow.select("a").attr("href").replaceAll("[^0-9]", ""),
                voteRow.select("a").text(),
                voteRow.select("img").attr("src"),
                new ArrayList<>()
        );
    }

    private Vote determineVote(Element voteRow) {
        Element voteSpan = voteRow.selectFirst("span.cvb");

        if (voteSpan.hasClass("voteup")) {
            return Vote.POSITIVE;
        }

        if (voteSpan.hasClass("votedown")) {
            return Vote.NEGATIVE;
        }

        if (voteSpan.hasClass("revoked")) {
            return Vote.REVOKED;
        }

        return null; // shouldn't get here
    }

    private Date findDate(Element voteRow) {
        return StringToDate.parsePostDate(voteRow.select("td:last-child").text(), true);
    }



}
