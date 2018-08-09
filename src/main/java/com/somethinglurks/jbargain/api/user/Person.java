package com.somethinglurks.jbargain.api.user;

import com.somethinglurks.jbargain.api.node.meta.attribute.Identifiable;

/**
 * Represents a person
 */
public interface Person extends Identifiable {

    /**
     * Gets the username
     *
     * @return Username
     */
    String getUsername();

    /**
     * Gets the URL to the avatar
     *
     * @return Avatar URL
     */
    String getAvatarUrl();

}
