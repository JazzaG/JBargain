package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.meta.attribute.Flaggable;
import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

/**
 * Represents an OzBargain node
 */
public interface Node extends Flaggable {

    /**
     * Gets the identifier
     *
     * @return Identifier
     */
    String getId();

    /**
     * Gets the title
     *
     * @return Title
     */
    String getTitle();

    /**
     * Gets the author
     *
     * @return Author
     */
    Author getAuthor();

    /**
     * Gets the date this was posted
     *
     * @return Post date
     */
    Date getPostDate();

    /**
     * Gets the category
     *
     * @return Category
     */
    Tag getCategory();

}
