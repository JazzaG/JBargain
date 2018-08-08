package com.somethinglurks.jbargain.api.node.teaser;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

/**
 * Represents a forum teaser
 */
public interface ForumTeaser extends Teaser, ForumNode {

    /**
     * Gets the author of the last reply
     *
     * @return Last reply author
     */
    Author getLastReplyAuthor();

    /**
     * Gets the date of the last reply
     *
     * @return Last reply date
     */
    Date getLastReplyDate();

}
