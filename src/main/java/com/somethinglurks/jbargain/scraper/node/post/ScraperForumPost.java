package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.post.ForumPost;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import com.somethinglurks.jbargain.scraper.util.date.StringToDate;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScraperForumPost extends ScraperPost implements ForumPost {

    public ScraperForumPost(Element element, ScraperUser user) {
        super(element, user);
    }

    @Override
    public List<PollOption> getPollOptions() {
        List<PollOption> pollOptions = new ArrayList<>();

        Author author;
        Date date;

        for (Element item : element.select("div#poll li")) {
            // Set author and date if item was suggested by another user
            if (item.select("div.suggest").size() == 1) {
                author = new Author(
                        item.select("div.suggest strong a").attr("href").replaceAll("[^0-9]", ""),
                        item.select("div.suggest strong a").text(),
                        "",
                        new ArrayList<>()
                );

                date = StringToDate.parsePostDate(item.select("div.suggest a").text(), true);
            } else {
                author = null;
                date = null;
            }

            // Add option
            pollOptions.add(new PollOption(
                    item.select("span.polltext").text(),
                    item.attr("data-oid"),
                    Integer.parseInt(item.select("div.n-vote > span > span").text()),
                    author,
                    date
            ));
        }

        return pollOptions;
    }

    @Override
    public boolean isPollExpired() {
        return element.select("div#poll span.marker.expired").size() == 1;
    }

}
