package com.somethinglurks.jbargain.api.node.post;

import com.somethinglurks.jbargain.api.node.DealNode;
import com.somethinglurks.jbargain.api.node.meta.Tag;

import java.util.List;

/**
 * Represents a deal post
 */
public interface DealPost extends Post, DealNode {

    /**
     * Gets a list of user-defined tags the author has attached
     *
     * @return List of tags
     */
    List<Tag> getTags();

}
