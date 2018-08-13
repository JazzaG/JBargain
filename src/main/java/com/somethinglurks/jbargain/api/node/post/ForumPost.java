package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;

import java.util.Date;
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

    /**
     * Gets the date this poll will expire.
     *
     * @return Expiry date of poll, or null if the poll has already expired or will not expire
     */
    Date getPollExpiryDate();

    /**
     * Gets whether the poll is open to suggestions
     *
     * @return True if users can suggest options, false if not
     */
    boolean canSuggestPollOptions();

    /**
     * Gets whether this poll allows users to change their votes
     *
     * @return True if users can change votes, false if not
     */
    boolean canChangeVotes();

}
