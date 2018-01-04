package com.somethinglurks.jbargain.api.node.meta.attribute;

import com.somethinglurks.jbargain.api.node.meta.Flag;

import java.util.List;

/**
 * Represents something that can have flags
 */
public interface Flaggable {

    /**
     * Gets the list of flags
     *
     * @return List of flags
     */
    List<Flag> getFlags();

}
