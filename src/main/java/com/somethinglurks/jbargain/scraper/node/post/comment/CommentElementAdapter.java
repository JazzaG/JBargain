package com.somethinglurks.jbargain.scraper.node.post.comment;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;

public class CommentElementAdapter extends Comment {

    private Element comment;
    private Elements children;

    private int level;

    public CommentElementAdapter(Element element, int level) {
        super(null, null, null, null, 0, level);

        this.comment = element.select("div.comment-wrap").first();
        this.children = element.select("> ul.comment");

        this.level = level;
    }

    @Override
    public String getDescription() {
        return comment.select("div.content").html();
    }

    @Override
    public String getId() {
        return comment.id().replaceAll("[^0-9]", "");
    }

    @Override
    public Author getAuthor() {
        return new Author(
                comment.select("div.submitted a").attr("href").replaceAll("[^0-9]", ""),
                comment.select("div.submitted strong").text(),
                comment.select("img.gravatar").attr("src"),
                Flags.createFromElements(comment.select("div.submitted span"))
        );
    }

    @Override
    public Date getDate() {
        return StringToDate.parsePostDate(comment.select("div.submitted").text(), true);
    }

    @Override
    public int getScore() {
        String value = comment.select("span.cvc").text().replaceAll("[^0-9\\-]", "");

        return Integer.parseInt(value);
    }

}
