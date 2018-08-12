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
     * Gets the list of voters who have voted on this content
     *
     * @return List of voters
     */
    List<Voter> getVoters();

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
     * Reveal the comment
     *
     * @param listener Task to execute when comment is revealed
     */
    void reveal(RevealListener listener);

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
     * Handles a comment that was revealed
     */
    interface RevealListener {

        /**
         * Called when a comment is revealed
         */
        void onCommentReveal();
    }
}
