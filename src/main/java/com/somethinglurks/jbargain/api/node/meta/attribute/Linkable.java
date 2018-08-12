package com.somethinglurks.jbargain.api.node.meta.attribute;

/**
 * Represents something that links to another website
 */
public interface Linkable {

    /**
     * Gets the target website domain name
     *
     * @return Target website
     */
    String getWebsite();

    /**
     * Gets the thumbnail URL
     *
     * @return Thumbnail URL
     */
    String getThumbnailUrl();

}
