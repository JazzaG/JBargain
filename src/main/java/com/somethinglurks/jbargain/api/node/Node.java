package com.somethinglurks.jbargain.api.node;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.meta.attribute.Flaggable;
import com.somethinglurks.jbargain.api.node.meta.attribute.Identifiable;

import java.util.Date;

/**
 * Represents an OzBargain node
 */
public interface Node extends Identifiable, Flaggable {

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
