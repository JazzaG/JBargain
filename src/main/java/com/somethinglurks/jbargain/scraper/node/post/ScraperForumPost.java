package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.post.ForumPost;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
import com.somethinglurks.jbargain.scraper.node.post.poll.ScraperPollOption;
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

        for (Element item : element.select("div#poll li")) {
            pollOptions.add(new ScraperPollOption(item, this.getId()));
        }

        return pollOptions;
    }

    @Override
    public boolean isPollExpired() {
        return element.select("div#poll span.marker.expired").size() == 1;
    }

    @Override
    public Date getPollExpiryDate() {
        // Check if poll has an expiration date
        if (isPollExpired() || element.select("div#poll span.options i.fa-calendar").size() == 0) {
            return null;
        } else {
            String dateString = element.select("div#poll span.options")
                    .text()
                    .substring(4); // Ignore the three-character day, the comma, and the space before the date
            return StringToDate.parsePostDate(dateString, true);
        }
    }

    @Override
    public boolean canSuggestPollOptions() {
        return element.select("div#poll span.options i.fa-comment").size() == 1;
    }

    @Override
    public boolean canChangeVotes() {
        return element.select("div#poll span.options i.fa-exchange").size() == 1;
    }
}
