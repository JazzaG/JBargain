package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperComment implements Comment {

    private Element comment;
    private int level;

    private boolean hasFetchedVoteData = false;
    private int positiveVotes;
    private int negativeVotes;
    private List<Voter> voters;

    public ScraperComment(Element element, int level) {
        this.comment = element.select("div.comment-wrap").first();
        this.level = level;
    }

    private void fetchVoteData() {
        if (!hasFetchedVoteData) {
            hasFetchedVoteData = true;

            // TODO
        }
    }

    @Override
    public String getDescription() {
        return comment.select("div.content").html();
    }

    @Override
    public String getId() {
        return comment.id().replaceAll("[^0-9]", "");
    }

    @Override
    public Author getAuthor() {
        return new Author(
                comment.select("div.submitted a").attr("href").replaceAll("[^0-9]", ""),
                comment.select("div.submitted strong").text(),
                comment.select("img.gravatar").attr("src"),
                Flags.createFromElements(comment.select("div.submitted span"))
        );
    }

    @Override
    public Date getDate() {
        return StringToDate.parsePostDate(comment.select("div.submitted").text(), true);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getScore() {
        String value = comment.select("span.cvc").text().replaceAll("[^0-9\\-]", "");

        return Integer.parseInt(value);
    }

    @Override
    public int getPositiveVotes() {
        fetchVoteData();
        return positiveVotes;
    }

    @Override
    public int getNegativeVotes() {
        fetchVoteData();
        return negativeVotes;
    }

    @Override
    public List<Voter> getVoters() {
        fetchVoteData();
        return voters;
    }

    @Override
    public Vote getUserVote() {
        return null; // TODO
    }
}
