package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Linkable;
import com.somethinglurks.jbargain.api.node.meta.attribute.NodeVotesViewable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;
import java.util.List;

/**
 * Represents a deal node
 */
public interface DealNode extends Node, Linkable, Describable, Votable, NodeVotesViewable {

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

    /**
     * Gets whether this deal is a freebie
     *
     * @return True if this deal is a freebie, false otherwise
     */
    boolean isFreebie();

    /**
     * Gets the coupon codes on this deal, if any
     *
     * @return List of coupon codes, or empty list if no coupon codes
     */
    List<String> getCouponCodes();

}
