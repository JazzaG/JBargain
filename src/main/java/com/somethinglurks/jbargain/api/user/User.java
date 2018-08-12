package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.node.meta.Vote;
import com.somethinglurks.jbargain.api.node.meta.attribute.Votable;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.exception.VoteException;
import com.somethinglurks.jbargain.api.user.notification.Notification;

import java.io.Serializable;
import java.util.List;

/**
 * Represents an authenticated user
 */
public interface User extends Person, Serializable {

    /**
     * Gets a list of notifications at the current time
     *
     * @return List of notifications
     */
    List<Notification> getNotifications();

    /**
     * Replies to a post
     *
     * @param post Post to reply to
     * @return ReplyBuilder object, to construct the reply
     */
    ReplyBuilder replyTo(Post post);

    /**
     * Replies to a comment
     *
     * @param comment Comment to reply to
     * @return ReplyBuilder object, to construct the reply
     */
    ReplyBuilder replyTo(Comment comment);

    /**
     * Casts a vote
     *
     * @param votable Content to vote on
     * @param vote Vote to cast
     * @throws VoteException if the vote could not be cast
     */
    void vote(Votable votable, Vote vote) throws VoteException;

    /**
     * Revokes a vote
     *
     * @param votable Content to revoke vote from
     * @throws VoteException if the vote could not be revoked
     */
    void revokeVote(Votable votable) throws VoteException;

}
