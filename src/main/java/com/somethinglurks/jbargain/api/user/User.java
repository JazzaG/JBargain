package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.exception.VoteException;
import com.somethinglurks.jbargain.api.user.notification.Notification;

import java.util.List;

/**
 * Represents an authenticated user
 */
public interface User extends Person {

    /**
     * Gets a list of notifications at the current time
     *
     * @return List of notifications
     */
    List<Notification> getNotifications();

    /**
     * Adds a comment to a post
     *
     * @param post Post to add comment to
     * @param reply Reply contents
     * @param associated Flag to indicate if user is associated with the post in any way
     * @return True on success, false otherwise
     */
    boolean replyTo(Post post, String reply, boolean associated);

    /**
     * Adds a comment to another comment
     *
     * @param comment Comment to reply to
     * @param reply Reply contents
     * @param associated Flag to indicate if user is associated with the post in any way
     * @return True on success, false otherwise
     */
    boolean replyTo(Comment comment, String reply, boolean associated);

    /**
     * Adds a vote to a post
     *
     * @param post Post to vote on
     * @param vote Vote value
     * @throws VoteException if the vote could not be cast
     */
    void vote(Post post, Vote vote) throws VoteException;

    /**
     * Adds a vote to a comment
     *
     * @param comment Comment to vote on
     * @param vote Vote value
     * @throws VoteException if the vote could not be cast
     */
    void vote(Comment comment, Vote vote) throws VoteException;

    /**
     * Revokes a vote from a post
     *
     * @param post Post to revoke vote from
     * @throws VoteException if the vote could not be revoked
     */
    void revokeVote(Post post) throws VoteException;

    /**
     * Revokes a vote from a comment
     *
     * @param comment Comment to revoke vote from
     * @throws VoteException if the vote could not be revoked
     */
    void revokeVote(Comment comment) throws VoteException;


}
