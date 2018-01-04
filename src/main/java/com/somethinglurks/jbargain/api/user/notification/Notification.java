package com.somethinglurks.jbargain.api.user.notification;

public class Notification {

    private String text;
    private String url;

    public Notification(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
