package com.somethinglurks.jbargain.api.user;

/**
 * Represents a person
 */
public interface Person {

    /**
     * Gets the identifier
     *
     * @return Identifier
     */
    String getId();

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
