package com.somethinglurks.jbargain.api.node.post.poll;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;

/**
 * Represents an option in a forum poll
 */
public interface PollOption extends Describable, Votable {

    /**
     * Gets the ID of the node this poll option belongs to
     *
     * @return ID of parent node
     */
    String getNodeId();

    /**
     * Gets the score of this item, which is equal to the positive votes minus the negative votes
     *
     * @return Score
     */
    int getScore();

    /**
     * Get the user who suggested this option
     *
     * @return Author of option
     */
    Author getAuthor();

    /**
     * Gets the date this option was suggested
     *
     * @return Date of suggestion
     */
    Date getPostDate();

}
