package com.somethinglurks.jbargain.scraper.node.meta;

import com.somethinglurks.jbargain.api.node.meta.Flag;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FlagListFactory {

    private FlagListFactory() {
    }

    public static List<Flag> createFromElements(Elements elements) {
        List<Flag> flags = new ArrayList<>();

        Flag flag;
        for (Element element : elements) {
            flag = Flag.getFlagByClass(element.className(), element.text());

            if (flag != null) {
                flags.add(flag);
            }
        }

        return flags;
    }
}
