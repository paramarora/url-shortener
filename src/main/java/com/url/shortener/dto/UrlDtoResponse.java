package com.url.shortener.dto;

public class UrlDtoResponse {
    private String shortUrl;
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString() {
        return "UrlDto{" +
                ", shortUrl='" + shortUrl + '\'' +
                ", count=" + count +
                '}';
    }
}
