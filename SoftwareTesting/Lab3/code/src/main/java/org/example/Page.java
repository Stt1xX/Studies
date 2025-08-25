package org.example;

public enum Page {
    BASE_PAGE("https://www.meetup.com"),
    EVENTS_PAGE("https://www.meetup.com/ru-RU/find/");

    private final String url;

    Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
