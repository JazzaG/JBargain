package com.somethinglurks.jbargain.api.node.meta.attribute;

/**
 * Represents something that can be voted on
 */
public interface Votable {

    /**
     * Gets the number of positive votes
     *
     * @return Positive vote
     */
    int getPositiveVotes();

    /**
     * Gets the number of negative votes
     *
     * @return Negative votes
     */
    int getNegativeVotes();

}
