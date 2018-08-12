package com.somethinglurks.jbargain.api.node.meta.attribute;

import com.somethinglurks.jbargain.api.node.meta.Voter;

import java.util.List;

/**
 * Represents content where the list of votes can be viewed
 */
public interface VotesViewable {

    /**
     * Retrieves the list of voters. This is only available if there is an authenticated user.
     *
     * @return List of voters, or an empty list if there are no voters, or null if there is no authenticated user
     */
    List<Voter> getVoters();

}
