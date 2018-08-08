package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
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
     * @return True on success, false otherwise
     */
    boolean vote(Post post, Vote vote);

    /**
     * Adds a vote to a comment
     *
     * @param comment Comment to vote on
     * @param vote Vote value
     * @return True on success, false otherwise
     */
    boolean vote(Comment comment, Vote vote);

    enum Vote {
        POSITIVE("1"),
        NEGATIVE("-1");

        public String value;

        Vote(String value) {
            this.value = value;
        }
    }

}
