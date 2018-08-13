package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.Node;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;

import java.util.Iterator;

/**
 * Represents an OzBargain Post
 */
public interface Post extends Node, Describable {

    /**
     * Gets if commenting is closed on this post
     *
     * @return True if comments are closed, false if not
     */
    boolean isDiscussionClosed();

    /**
     * Gets an iterator of the comments on this posts
     *
     * @param ignoreUnpublishedComments Flag to ignore unpublished comments. This only applies if there is an
     *                                  authenticated user
     * @return Comments iterator
     */
    Iterator<Comment> getComments(boolean ignoreUnpublishedComments);

}
