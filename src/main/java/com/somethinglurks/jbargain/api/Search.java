package com.somethinglurks.jbargain.api;

import com.somethinglurks.jbargain.api.node.teaser.Teaser;

import java.util.List;

public interface Search {

    Search sortBy(SortOption sortOption);

    Search forumTopicOnly();

    Search dealsOnly(boolean noExpired);

    Search competitionsOnly();

    List<Teaser> getResults();

    enum SortOption {
        RELEVANCE,
        POST_DATE,
        LAST_COMMENT
    }

}
