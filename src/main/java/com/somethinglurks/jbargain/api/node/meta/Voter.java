package com.somethinglurks.jbargain.api.node.meta;

import java.util.Date;

/**
 * Represents a voter
 */
public class Voter {

    private Author author;
    private Vote vote;
    private Date date;

    public Voter(Author author, Vote vote, Date date) {
        this.author = author;
        this.vote = vote;
        this.date = date;
    }

    /**
     * Gets the author of this vote
     *
     * @return Vote author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Gets the vote casted
     *
     * @return Vote casted
     */
    public Vote getVote() {
        return vote;
    }

    /**
     * Gets the date this vote was cast
     *
     * @return Date of vote
     */
    public Date getDate() {
        return date;
    }
}
