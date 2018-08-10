package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.node.post.comment.CommentIterator;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScraperPost implements Post {

    protected Element element;
    protected ScraperUser user;

    private CommentIterator commentIterator;

    ScraperPost(Element element, ScraperUser user) {
        this.element = element;
        this.user = user;
        this.commentIterator = new CommentIterator(getId(), element, user);
    }

    @Override
    public Iterator<Comment> getComments() {
        return commentIterator;
    }

    @Override
    public String getId() {
        Matcher matcher = Pattern.compile("\\/([0-9]+)").matcher(element.baseUri());
        matcher.find();

        return matcher.group(1);
    }

    @Override
    public String getTitle() {
        return element.select("span.title").text();
    }

    @Override
    public Author getAuthor() {
        return new Author(
                element.select("div.submitted a").first().attr("href").replaceAll("[^0-9]", ""),
                element.select("div.submitted a").first().text(),
                element.select("div.n-left img.gravatar").attr("src"),
                Flags.createFromElements(element.select("div.submitted span"))
        );
    }

    @Override
    public Date getPostDate() {
        return StringToDate.parsePostDate(element.select("div.submitted").text(), true);
    }

    @Override
    public Tag getCategory() {
        return new Tag(
                element.select("div.taxonomy span a").first().text(),
                element.select("div.taxonomy span a").first().attr("href")
        );
    }

    @Override
    public String getDescription() {
        return element.select("div.node div.content").html();
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("h1.title span"));
    }
}
