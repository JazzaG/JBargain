package com.somethinglurks.jbargain.api.node.meta;

public class Tag {

    private String text;
    private String endpoint;

    public Tag(String text, String endpoint) {
        this.text = text;
        this.endpoint = endpoint;
    }

    public String getText() {
        return text;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
