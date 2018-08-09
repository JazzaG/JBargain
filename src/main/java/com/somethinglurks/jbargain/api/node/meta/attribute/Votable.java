package com.somethinglurks.jbargain.api.node.meta.attribute;

import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.Voter;

import java.util.List;

/**
 * Represents something that can be voted on
 */
public interface Votable extends Identifiable {

    /**
     * Gets the score of this item, which is equal to the positive votes minus the negative votes
     *
     * @return Score
     */
    int getScore();

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

    /**
     * Gets the list of voters who have voted on this content
     *
     * @return List of voters
     */
    List<Voter> getVoters();

    /**
     * Gets the vote casted by the authenticated user, if any
     *
     * @return Vote casted by the authenticated user, or null if no vote casted
     */
    Vote getUserVote();

}
