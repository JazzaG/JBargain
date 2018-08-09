package com.somethinglurks.jbargain.api.node.meta;

/**
 * Represents a vote on a post or comment
 */
public enum Vote {
    POSITIVE(1),
    REVOKED(0),
    NEGATIVE(-1);

    public int value;

    Vote(int value) {
        this.value = value;
    }
}
