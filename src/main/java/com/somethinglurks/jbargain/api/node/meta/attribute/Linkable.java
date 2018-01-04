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

    /**
     * Gets the number of clicks on this link
     *
     * @return Number of links
     */
    int getNumberOfClicks();

}
