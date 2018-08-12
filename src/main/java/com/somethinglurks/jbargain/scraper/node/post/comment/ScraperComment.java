package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.OzBargainApi;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.List;

public class ScraperComment implements Comment {

    public Element element;
    private String id;
    private int level;

    private Type type;

    private boolean hasFetchedVoteData = false;
    private List<Voter> voters;

    public ScraperComment(Element element, String id, int level, Type type) {
        this.element = element;
        this.id = id;
        this.level = level;
        this.type = type;
    }

    private void fetchVoteData() {
        if (!hasFetchedVoteData) {
            hasFetchedVoteData = true;

            // TODO
        }
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
    public List<Voter> getVoters() {
        // TODO
        fetchVoteData();
        return voters;
    }

    @Override
    public Vote getUserVote() {
        return null; // TODO
    }
}
