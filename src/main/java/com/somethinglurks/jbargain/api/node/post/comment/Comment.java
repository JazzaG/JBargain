package com.somethinglurks.jbargain.api.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.attribute.Describable;
import com.somethinglurks.jbargain.api.node.meta.Author;

import java.util.Date;

/**
 * Represents a comment on a post
 */
public class Comment implements Describable {

    private String id;
    private Author author;
    private Date date;

    private String description;
    private int score;

    private int level;

    public Comment(String id, Author author, Date date, String description, int score, int level) {
        this.id = id;
        this.author = author;
        this.date = date;
        this.description = description;
        this.score = score;
        this.level = level;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Gets the comment identifier
     *
     * @return Comment identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the author
     *
     * @return Author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Gets the date this comment was posted
     *
     * @return Post date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the votes on this comment
     *
     * @return Score
     */
    public int getScore() {
        return score;
}

    /**
     * Gets the nested-level of this comment
     *
     * @return Comment level
     */
    public int getLevel() {
        return level;
    }
}
