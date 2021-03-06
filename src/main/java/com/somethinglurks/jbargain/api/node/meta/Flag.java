package com.somethinglurks.jbargain.api.node.meta;

/**
 * Represents a type of flag on a post, author, or comment
 */
public enum Flag {

    STICKY,

    AUTHOR_LEARNER,
    AUTHOR_RED_PLATE,
    AUTHOR_GREEN_PLATE,
    AUTHOR_ASSOCIATED,
    AUTHOR_STORE_REP,
    AUTHOR_EMPLOYEE,
    AUTHOR_REFERRER,
    AUTHOR_THIRD_PARTY,
    AUTHOR_MODERATOR,
    AUTHOR_VOTED_POSITIVE,
    AUTHOR_VOTED_NEGATIVE,

    DEAL_EXPIRED,
    DEAL_OUT_OF_STOCK,
    DEAL_TARGETED,
    DEAL_UPCOMING,

    FORUM_CLOSED,
    FORUM_POLL,
    FORUM_RESOLVED,

    COMPETITION_CLOSED

}
