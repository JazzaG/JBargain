package com.somethinglurks.jbargain.api.node.meta;

import com.somethinglurks.jbargain.api.node.meta.attribute.Flaggable;
import com.somethinglurks.jbargain.api.user.Person;

import java.util.List;

/**
 * Represents an author of a post
 */
public class Author implements Person, Flaggable {

    private String id;
    private String username;
    private String avatarUrl;
    private List<Flag> flags;

    public Author(String id, String username, String avatarUrl, List<Flag> flags) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.flags = flags;
    }

    @Override
    public List<Flag> getFlags() {
        return flags;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String toString() {
        return String.format("{ %s: \"%s\" }", this.id, this.username);
    }

}
