package com.somethinglurks.jbargain.api.node.teaser;

import com.somethinglurks.jbargain.api.node.Node;

/**
 * Represents an OzBargain teaser
 */
public interface Teaser extends Node {

    /**
     * Gets the number of comments on the post
     *
     * @return Number of comments
     */
    int getNumberOfComments();

}
