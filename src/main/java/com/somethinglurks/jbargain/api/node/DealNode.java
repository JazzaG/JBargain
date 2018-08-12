package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Linkable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;
import java.util.List;

/**
 * Represents a deal node
 */
public interface DealNode extends Node, Linkable, Describable, Votable {

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
     * Gets the start date of the deal
     *
     * @return Start date
     */
    Date getStartDate();

    /**
     * Gets the end date (expiry date) of the deal
     *
     * @return End date
     */
    Date getEndDate();

}
