package com.somethinglurks.jbargain.scraper.util.integer;

import org.jsoup.nodes.Element;

public final class StringToInteger {

    private StringToInteger() {

    }

    public static int parseSelector(Element element, String selector) {
        String value = element.select(selector).text().replaceAll("[^0-9]", "");

        return Integer.parseInt(value);
    }

}
