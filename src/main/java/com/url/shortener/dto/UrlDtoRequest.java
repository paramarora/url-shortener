package com.url.shortener.dto;

public class UrlDtoRequest {
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "UrlDtoRequest{" +
                "longUrl='" + longUrl + '\'' +
                '}';
    }
}
