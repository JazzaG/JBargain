package com.somethinglurks.jbargain.api.node.teaser;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

/**
 * Represents a forum teaser
 */
public interface ForumTeaser extends Teaser, ForumNode {

    /**
     * Gets the author of the last reply, if available
     *
     * @return Last reply author, or null if not available
     */
    Author getLastReplyAuthor();

    /**
     * Gets the date of the last reply, if available
     *
     * @return Last reply date, or null if not available
     */
    Date getLastReplyDate();

}
