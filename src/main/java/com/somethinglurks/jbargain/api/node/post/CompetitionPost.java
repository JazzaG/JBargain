package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.CompetitionNode;

/**
 * Represents a competition post
 */
public interface CompetitionPost extends Post, CompetitionNode {

    /**
     * Gets whether this competition has multiple draws
     *
     * @return True if this competition has multiple draws, false otherwise
     */
    boolean hasMultipleDraws();

    /**
     * Gets the description of the prize
     *
     * @return Prize description
     */
    String getPrizeDescription();

    /**
     * Gets the URL to the terms and conditiosn page
     *
     * @return Terms and conditions URL
     */
    String getTermsAndConditionsUrl();

}
