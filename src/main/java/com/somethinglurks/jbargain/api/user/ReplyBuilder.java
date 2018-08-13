package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.user.exception.ReplyException;

/**
 * Constructs a reply to a post or comment
 */
public interface ReplyBuilder {

    /**
     * Adds the content of the reply
     *
     * @param content Reply contents
     * @return this object, for chaining
     */
    ReplyBuilder addContent(String content);

    /**
     * Adds a poll suggestion, if applicable
     *
     * @param pollSuggestion Poll suggestion
     * @return this object, for chaining
     */
    ReplyBuilder addPollSuggestion(String pollSuggestion);

    /**
     * Sets whether the user is associated with this post
     *
     * @param isAssociated Boolean flag to indicate association
     * @return this object, for chaining
     */
    ReplyBuilder setAssociated(boolean isAssociated);

    /**
     * Submits the reply
     *
     * @throws ReplyException if something goes wrong
     */
    void reply() throws ReplyException;

}
