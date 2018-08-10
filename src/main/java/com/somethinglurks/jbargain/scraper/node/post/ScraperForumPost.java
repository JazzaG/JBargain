package com.somethinglurks.jbargain.scraper.node.post;

import com.somethinglurks.jbargain.api.node.post.ForumPost;
import com.somethinglurks.jbargain.api.node.post.poll.PollOption;
import com.somethinglurks.jbargain.scraper.node.post.poll.ScraperPollOption;
import com.somethinglurks.jbargain.scraper.user.ScraperUser;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class ScraperForumPost extends ScraperPost implements ForumPost {

    public ScraperForumPost(Element element, ScraperUser user) {
        super(element, user);
    }

    @Override
    public List<PollOption> getPollOptions() {
        List<PollOption> pollOptions = new ArrayList<>();

        for (Element item : element.select("div#poll li")) {
            pollOptions.add(new ScraperPollOption(item));
        }

        return pollOptions;
    }

    @Override
    public boolean isPollExpired() {
        return element.select("div#poll span.marker.expired").size() == 1;
    }

}
