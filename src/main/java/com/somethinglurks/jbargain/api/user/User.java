package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.api.user.actor.Actor;
import com.somethinglurks.jbargain.api.user.notification.Notification;

import java.util.List;

/**
 * Represents an authenticated user
 */
public interface User extends Actor {

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
     * @param up Flag to indicate if vote is positive
     * @return True on success, false otherwise
     */
    boolean vote(Post post, boolean up);

    /**
     * Adds a vote to a comment
     *
     * @param comment Comment to vote on
     * @param up Flag to indicate if vote is positive
     * @return True on success, false otherwise
     */
    boolean vote(Comment comment, boolean up);

}
