package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Linkable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;

/**
 * Represents a deal node
 */
public interface DealNode extends Node, Linkable, Describable, Votable {

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
