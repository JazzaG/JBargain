package com.somethinglurks.jbargain.scraper.node.meta;

import com.somethinglurks.jbargain.api.node.meta.Flag;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FlagList extends ArrayList<Flag> {
    
    private static final Object[][] OPTIONS = {
            { Flag.STICKY, "fa-thumb-tack", "" },

            { Flag.AUTHOR_LEARNER, "plate learner", "" },
            { Flag.AUTHOR_RED_PLATE, "plate redp", "" },
            { Flag.AUTHOR_GREEN_PLATE, "plate greenp", "" },
            { Flag.AUTHOR_ASSOCIATED, "role storerep", "Associated" },
            { Flag.AUTHOR_STORE_REP, "role storerep", "Store Rep" },
            { Flag.AUTHOR_EMPLOYEE, "role storerep", "Employee" },
            { Flag.AUTHOR_REFERRER, "role referrer", "" },
            { Flag.AUTHOR_THIRD_PARTY, "role referrer", "Third Party" },
            { Flag.AUTHOR_MODERATOR, "role moderator", "Moderator" },
            { Flag.AUTHOR_VOTED_POSITIVE, "cvb voteup", "" },
            { Flag.AUTHOR_VOTED_NEGATIVE, "cvb votedown", "" },

            { Flag.DEAL_EXPIRED, "tagger expired", "EXPIRED" },
            { Flag.DEAL_OUT_OF_STOCK, "tagger expired", "OUT OF STOCK" },
            { Flag.DEAL_TARGETED, "tagger targeted", "TARGETED" },
            { Flag.DEAL_UPCOMING, "tagger upcoming", "" },
        
            { Flag.FORUM_CLOSED, "forum-close", "" },
            { Flag.FORUM_POLL, "forum-poll", "" },
            { Flag.FORUM_RESOLVED, "tagger resolved", "" },
        
            { Flag.COMPETITION_CLOSED, "tagger expired", "CLOSED" },
    };

    public FlagList(Elements elements) {
        Flag flag;
        String cssClass;
        String text;
        
        for (Element element : elements) {
            for (Object[] option : OPTIONS) {
                flag = (Flag) option[0];
                cssClass = (String) option[1];
                text = (String) option[2];
                
                // Find by class
                if (element.className().contains(cssClass)) {
                    // Match by text if exists
                    if (!text.equals("") && !element.text().equalsIgnoreCase(text)) {
                        continue;
                    }
                    
                    // Add flag to list
                    add(flag);
                }
            }
        }
    }

}
