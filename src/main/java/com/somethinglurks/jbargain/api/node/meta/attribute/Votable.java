package com.somethinglurks.jbargain.api.node.meta.attribute;

import com.somethinglurks.jbargain.api.node.meta.Vote;

/**
 * Represents something that can be voted on
 */
public interface Votable extends Identifiable {

    /**
     * Gets the vote casted by the authenticated user, if any
     *
     * @return Vote casted by the authenticated user, or null if no vote casted
     */
    Vote getUserVote();

}
