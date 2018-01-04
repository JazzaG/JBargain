package com.somethinglurks.jbargain.api;

import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.teaser.Teaser;
import com.somethinglurks.jbargain.api.user.AuthenticationResult;
import com.somethinglurks.jbargain.api.user.User;

import java.io.IOException;
import java.util.List;

/**
 * Provides an entry-point to the JBargain API
 */
public interface JBargain {

    /**
     * Authenticates a user with a username-password combination
     *
     * @param username Username
     * @param password Password
     * @return Result object, which contains the result of authentication and the user, if any
     */
    AuthenticationResult authenticateUser(String username, String password);

    /**
     * Gets a post by its identifier
     *
     * @param id Identifier
     * @param user User, if available
     * @return Post object
     * @throws IOException if anything goes wrong
     */
    Post getPostById(String id, User user) throws IOException;

    /**
     * Gets a list of teasers by tag
     *
     * @param tag Tag
     * @param user User, if available
     * @param page Page number to search
     * @return List of teasers
     * @throws IOException if anything goes wrong
     */
    List<Teaser> getFeedByTag(Tag tag, User user, int page) throws IOException;

}
