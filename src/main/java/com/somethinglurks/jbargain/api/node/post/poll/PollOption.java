package com.somethinglurks.jbargain.api.node.post.poll;

import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

/**
 * Represents an option in a forum poll
 */
public class PollOption {

    private String option;
    private String optionId;
    private int votes;

    private Author author;
    private Date postDate;

    public PollOption(String option, String optionId, int votes, Author author, Date postDate) {
        this.option = option;
        this.optionId = optionId;
        this.votes = votes;
        this.author = author;
        this.postDate = postDate;
    }

    /**
     * Gets the poll option
     *
     * @return Poll option
     */
    public String getOption() {
        return option;
    }

    /**
     * Gets the identifier
     *
     * @return Identifier
     */
    public String getOptionId() {
        return optionId;
    }

    /**
     * Gets the number of votes this option has
     *
     * @return Number of votes
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Get the user who suggested this option
     *
     * @return Author of option
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Gets the date this option was suggested
     *
     * @return Date of suggestion
     */
    public Date getPostDate() {
        return postDate;
    }

}
