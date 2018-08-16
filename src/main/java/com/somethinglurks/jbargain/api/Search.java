package com.somethinglurks.jbargain.api;

import com.somethinglurks.jbargain.api.node.teaser.Teaser;

import java.util.List;

public interface Search {

    Search sortBy(SortOption sortOption);

    Search filterBy(Type type);

    List<Teaser> getResults();

    enum SortOption {
        RELEVANCE,
        POST_DATE,
        LAST_COMMENT
    }

    enum Type {
        CLASSIFIED,
        COMPETITION,
        DEALS,
        DEALS_NO_EXPIRED,
        FORUM_TOPICS
    }

}
