package com.somethinglurks.jbargain.api;

import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.api.user.User;
import com.somethinglurks.jbargain.api.user.exception.AuthenticationException;

import java.io.IOException;
import java.util.Iterator;

/**
 * Provides an entry-point to the JBargain API
 */
public interface JBargain {

    /**
     * Authenticates a user with a username-password combination
     *
     * @param username Username
     * @param password Password
     * @return Authenticated User object
     * @throws AuthenticationException if something goes wrong during authentication
     */
    User authenticateUser(String username, String password) throws AuthenticationException;

    /**
     * Gets a post by its identifier
     *
     * @param id Identifier
     * @param user User
     * @return Post object
     * @throws IOException if anything goes wrong
     */
    Post getPostById(String id, User user) throws IOException;

    /**
     * Gets a post by its identifier
     *
     * @param id Identifier
     * @return Post object
     * @throws IOException if anything goes wrong
     */
    Post getPostById(String id) throws IOException;

    /**
     * Gets a list of teasers by tag
     *
     * @param tag Tag
     * @param user User
     * @return List of teasers
     * @throws IOException if anything goes wrong
     */
    Iterator<Teaser> getTeasersByTag(Tag tag, User user) throws IOException;

    /**
     * Gets a list of teasers by tag
     *
     * @param tag Tag
     * @return List of teasers
     * @throws IOException if anything goes wrong
     */
    Iterator<Teaser> getTeasersByTag(Tag tag) throws IOException;

}
