package com.somethinglurks.jbargain.api.node.meta;

import java.util.regex.Pattern;

public enum Flag {

    AUTHOR_LEARNER("plate learner", ""),
    AUTHOR_RED_PLATE("plate redp", ""),
    AUTHOR_GREEN_PLATE("plate greenp", ""),
    AUTHOR_ASSOCIATED("role storerep", "Associated"),
    AUTHOR_STORE_REP("role storerep", "Store Rep"),
    AUTHOR_EMPLOYEE("role storerep", "Employee"),
    AUTHOR_REFERRER("role referrer", ""),
    AUTHOR_THIRD_PARTY("role referrer", "Third Party"),

    DEAL_EXPIRED("tagger expired", "EXPIRED"),
    DEAL_OUT_OF_STOCK("tagger expired", "OUT OF STOCK"),
    DEAL_TARGETED("tagger targeted", "TARGETED"),
    DEAL_UPCOMING("tagger upcoming", ""),

    FORUM_STICKY("forum-sticky", ""),
    FORUM_POLL("forum-poll", ""),
    FORUM_RESOLVED("tagger resolved", ""),

    COMPETITION_CLOSED("tagger expired", "CLOSED");

    private String cssClass;
    private String text;

    Flag(String cssClass, String text) {
        this.cssClass = cssClass;
        this.text = text;
    }

    public static Flag getFlagByClass(String cssClass, String text) {
        for (Flag flag : Flag.values()) {
            // Find by class
            if (flag.cssClass.contains(cssClass)) {
                // Also match by text if exists
                if (!flag.text.isEmpty()) {
                    if (!Pattern.compile(flag.text).matcher(text).find()) {
                        continue;
                    }
                }

                return flag;
            }
        }

        return null;
    }

}
