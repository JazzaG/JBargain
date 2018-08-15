package com.somethinglurks.jbargain.scraper.node.meta;

import com.somethinglurks.jbargain.api.node.meta.Author;
import com.somethinglurks.jbargain.api.node.meta.Flag;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class AuthorElementAdapter extends Author {

    private String id;
    private String username;
    private String avatarUrl;
    private List<Flag> flags;

    public AuthorElementAdapter(Element element, String anchorSelector, String imgSelector, String flagSelector) {
        super(null, null, null, null);

        this.id = element.select(anchorSelector).attr("href").replaceAll("[^0-9]", "");
        this.username = element.select(anchorSelector).text();
        this.avatarUrl = element.select(imgSelector).attr("src");

        if (flagSelector != null) {
            this.flags = Flags.createFromElements(element.select(flagSelector));
        } else {
            this.flags = new ArrayList<>();
        }
    }

    public AuthorElementAdapter(Element element) {
        super(null, null, null, null);

        this.id = element.select("a").attr("href").replaceAll("[^0-9]", "");
        this.username = element.select("a").text();

        if ((element = element.selectFirst("img")) != null) {
            this.avatarUrl = element.attr("src");
        } else {
            this.avatarUrl = "";
        }

        this.flags = new ArrayList<>();
    }

    @Override
    public List<Flag> getFlags() {
        return flags;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getAvatarUrl() {
        return avatarUrl;
    }
}
