package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.Node;
import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;

import java.util.Iterator;
import java.util.List;

/**
 * Represents an OzBargain Post
 */
public interface Post extends Node, Describable {

    /**
     * Gets an iterator of a list of comments on this post.
     *
     * <br />
     * This is designed in such a way that:
     * <ul>
     *     <li>If a user is authenticated; only one element will be present which contains all the comments</li>
     *     <li>If no user is authenticated; each element represents the comments on each page</li>
     * </ul>
     *
     * @return Iterator of List of comments
     */
    Iterator<List<Comment>> getComments();

}
