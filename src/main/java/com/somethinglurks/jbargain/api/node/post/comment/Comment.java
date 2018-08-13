package com.somethinglurks.jbargain.api.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Voter;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;
import java.util.List;

/**
 * Represents a comment on a post
 */
public interface Comment extends Describable, Votable {

    /**
     * Gets the score of this item, which is equal to the positive votes minus the negative votes
     *
     * @return Score
     */
    int getScore();

    /**
     * Gets what type of comment this is
     *
     * @return Type of comment
     */
    Type getType();

    /**
     * Gets the reason this comment was unpublished
     *
     * @return Reason this comment was unpublished, or null if comment is not unpublished or has no message
     */
    String getUnpublishedReason();

    /**
     * Fetches and stores the hidden comment
     */
    void reveal();

    /**
     * Gets the author
     *
     * @return Author
     */
    Author getAuthor();

    /**
     * Gets the date this comment was posted
     *
     * @return Post date
     */
    Date getDate();

    /**
     * Gets the nested-level of this comment
     *
     * @return Comment level
     */
    int getLevel();

    /**
     * Gets the votes on this comment
     *
     * @return Voters of this comment
     */
    Voters getVoters();

    /**
     * Represents the type of comment
     */
    enum Type {
        /** Visible comment */
        VISIBLE,

        /** Hidden comment, usually for having a low score */
        HIDDEN,

        /** A comment that is unpublished, usually by a moderator */
        UNPUBLISHED
    }

    /**
     * Holds information about the voters of this comment
     */
    interface Voters {
        /**
         * Gets the list of positive voters
         *
         * @return List containing the positive voters
         */
        List<Voter> getPositiveVoters();

        /**
         * Gets the number of negative voters
         *
         * @return Number of negative voters
         */
        int getNumberOfNegativeVoters();
    }
}
