package com.somethinglurks.jbargain.api.node.meta;

/**
 * Represents a voter
 */
public class Voter {

    private Author author;
    private Vote vote;

    public Voter(Author author, Vote vote) {
        this.author = author;
        this.vote = vote;
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
}
