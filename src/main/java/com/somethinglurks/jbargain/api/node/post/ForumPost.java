package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;

import java.util.List;

/**
 * Represents a forum post
 */
public interface ForumPost extends Post, ForumNode {

    /**
     * Gets the list of options in the poll
     *
     * @return List of poll options
     */
    List<PollOption> getPollOptions();

    /**
     * Gets whether the poll has expired
     *
     * @return true if the poll has expired, false otherwise
     */
    boolean isPollExpired();

}
