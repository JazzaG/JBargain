package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.attribute.Linkable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;

/**
 * Represents a competition node
 */
public interface CompetitionNode extends Node, Linkable, Votable {

    /**
     * Gets the draw date
     *
     * @return Draw date
     */
    Date getDrawDate();

    /**
     * Gets the total prize pool
     *
     * @return Prize pool
     */
    String getPrizePool();

    /**
     * Gets who the competition is open to
     *
     * @return Competition eligibility
     */
    String getOpenTo();

    /**
     * Gets the methods of entry
     *
     * @return Methods of entry
     */
    String getMethods();

    /**
     * Gets entry requirements
     *
     * @return Entry requirements
     */
    String getRequirements();

    /**
     * Gets the reported number of entrants
     *
     * @return Number of entrants
     */
    int getNumberOfEntrants();

    /**
     * Gets the reported number of winners
     *
     * @return Number of winners
     */
    int getNumberOfWinners();

}
