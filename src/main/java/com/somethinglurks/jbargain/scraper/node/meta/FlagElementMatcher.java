package com.somethinglurks.jbargain.scraper.node.meta;

import com.somethinglurks.jbargain.api.node.meta.Flag;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class FlagElementMatcher {

    private static final Map<Flag, Option> OPTIONS = new HashMap<Flag, Option>(){{
        put(Flag.AUTHOR_LEARNER, new Option("plate learner", ""));
        put(Flag.AUTHOR_RED_PLATE, new Option("plate redp", ""));
        put(Flag.AUTHOR_GREEN_PLATE, new Option("plate greenp", ""));
        put(Flag.AUTHOR_ASSOCIATED, new Option("role storerep", "Associated"));
        put(Flag.AUTHOR_STORE_REP, new Option("role storerep", "Store Rep"));
        put(Flag.AUTHOR_EMPLOYEE, new Option("role storerep", "Employee"));
        put(Flag.AUTHOR_REFERRER, new Option("role referrer", ""));
        put(Flag.AUTHOR_THIRD_PARTY, new Option("role referrer", "Third Party"));

        put(Flag.DEAL_EXPIRED, new Option("tagger expired", "EXPIRED"));
        put(Flag.DEAL_OUT_OF_STOCK, new Option("tagger expired", "OUT OF STOCK"));
        put(Flag.DEAL_TARGETED, new Option("tagger targeted", "TARGETED"));
        put(Flag.DEAL_UPCOMING, new Option("tagger upcoming", ""));

        put(Flag.FORUM_STICKY, new Option("forum-sticky", ""));
        put(Flag.FORUM_POLL, new Option("forum-poll", ""));
        put(Flag.FORUM_RESOLVED, new Option("tagger resolved", ""));

        put(Flag.COMPETITION_CLOSED, new Option("tagger expired", "CLOSED"));
    }};

    public static Flag matchFromElement(Element element) {
        Option option;
        for (Map.Entry<Flag, Option> entry : OPTIONS.entrySet()) {
            option = entry.getValue();

            // Find by class
            if (option.cssClass.contains(element.className())) {
                // Also match by text if exists
                if (!option.elementText.isEmpty()) {
                    if (!option.elementText.equalsIgnoreCase(element.text())) {
                        continue;
                    }
                }

                return entry.getKey();
            }
        }

        return null;
    }

    private static class Option {
        String cssClass;
        String elementText;

        Option(String cssClass, String elementText) {
            this.cssClass = cssClass;
            this.elementText = elementText;
        }
    }

}
