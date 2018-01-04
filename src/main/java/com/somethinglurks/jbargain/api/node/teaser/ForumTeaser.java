package com.somethinglurks.jbargain.api.node.teaser;

import com.somethinglurks.jbargain.api.node.ForumNode;
import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

public interface ForumTeaser extends Teaser, ForumNode {

    Author getLastReplyAuthor();

    Date getLastReplyDate();

}
