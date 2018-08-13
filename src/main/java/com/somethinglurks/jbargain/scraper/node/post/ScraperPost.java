package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import com.somethinglurks.jbargain.api.node.meta.Tag;
import com.somethinglurks.jbargain.api.node.post.Post;
import com.somethinglurks.jbargain.api.node.post.comment.Comment;
import com.somethinglurks.jbargain.scraper.ScraperJBargain;
import com.somethinglurks.jbargain.scraper.node.meta.AuthorElementAdapter;
import com.somethinglurks.jbargain.scraper.node.meta.Flags;
import com.somethinglurks.jbargain.scraper.node.post.comment.CommentIterator;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScraperPost implements Post {

    protected Element element;
    protected ScraperUser user;

    ScraperPost(Element element, ScraperUser user) {
        this.element = element;
        this.user = user;
    }

    @Override
    public Iterator<Comment> getComments(boolean ignoreUnpublishedComments) {
        Element targetElement = this.element;

        // Load the comments page if available and parameter flag is set
        // This is because the comments page does not show unpublished comments
        if (user != null && ignoreUnpublishedComments) {
            try {
                 targetElement = Jsoup
                        .connect(ScraperJBargain.HOST + "/node/" + getId() + "/comments")
                        .cookies(user.getCookies())
                        .get();
            } catch (IOException ignored) {

            }
        }

        return new CommentIterator(getId(), targetElement, user);
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
        return new AuthorElementAdapter(element,
                "div.submitted a",
                "div.n-left img.gravatar",
                "div.submitted span");
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
        Elements contentElements = element.select("div.node div.content");

        contentElements.select(".couponcode").remove();
        return contentElements.html();
    }

    @Override
    public List<Flag> getFlags() {
        return Flags.createFromElements(element.select("h1.title span"));
    }
}
