package com.somethinglurks.jbargain.api.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;

import java.util.Date;

/**
 * Represents a comment on a post
 */
public interface Comment extends Describable, Votable {

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
}
