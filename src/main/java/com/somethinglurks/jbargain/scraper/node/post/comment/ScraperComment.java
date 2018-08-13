package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.OzBargainApi;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import com.somethinglurks.jbargain.scraper.util.integer.StringToInteger;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperComment implements Comment {

    private Element element;
    private String id;
    private int level;

    private Type type;

    public ScraperComment(Element element, String id, int level, Type type) {
        this.element = element;
        this.id = id;
        this.level = level;
        this.type = type;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public String getUnpublishedReason() {
        if (this.type != Type.UNPUBLISHED) {
            return null;
        }

        String reason = element.select("em").text();

        // Trim the enclosing parenthesis
        return reason.substring(1, reason.length() - 1);
    }

    @Override
    public void reveal(RevealListener listener) {
        this.element = OzBargainApi.showComment(getId());

        if (listener != null) {
            listener.onCommentReveal();
        }
    }

    @Override
    public String getDescription() {
        return element.select("div.content").html();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Author getAuthor() {
        return new Author(
                element.select("div.submitted a").attr("href").replaceAll("[^0-9]", ""),
                element.select("div.submitted strong").text(),
                element.select("img.gravatar").attr("src"),
                Flags.createFromElements(element.select("div.submitted span"))
        );
    }

    @Override
    public Date getDate() {
        return StringToDate.parsePostDate(element.select("div.submitted").text(), true);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getScore() {
        switch (type) {
            case UNPUBLISHED:
                return 0;

            case HIDDEN:
                return 0;

            case VISIBLE:
            default:
                String value = element.select("span.cvc").text().replaceAll("[^0-9\\-]", "");
                return Integer.parseInt(value);
        }
    }

    @Override
    public Voters getVoters() {
        Element votersList = OzBargainApi.listCommentVotes(getId());
        List<Voter> positiveVoters = new ArrayList<>();

        // Build list of positive voters
        for (Element voteRow : votersList.select("tbody tr")) {
            positiveVoters.add(new Voter(
                    new Author(
                            voteRow.select("td:nth-child(3) a").attr("href").replaceAll("[^0-9]", ""),
                            voteRow.select("td:nth-child(3) a").text(),
                            voteRow.select("td:nth-child(3) img").attr("src"),
                            new ArrayList<>()
                    ),
                    Vote.POSITIVE,
                    StringToDate.parsePostDate(voteRow.selectFirst("td:last-child").text(), true)
            ));
        }

        // Get number of negative voters
        int negativeVoters = StringToInteger.parseSelector(votersList, "tbody tr:last-of-type");

        return new Voters() {
            @Override
            public List<Voter> getPositiveVoters() {
                return positiveVoters;
            }

            @Override
            public int getNumberOfNegativeVoters() {
                return negativeVoters;
            }
        };
    }

    @Override
    public Vote getUserVote() {
        if (element.select("div.c-vote.voteup").size() == 1) {
            return Vote.POSITIVE;
        } else if (element.select("div.c-vote.votedown").size() == 1) {
            return Vote.NEGATIVE;
        } else {
            return null;
        }
    }
}
