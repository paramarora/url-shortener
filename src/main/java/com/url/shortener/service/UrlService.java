package com.url.shortener.service;

import com.url.shortener.dto.UrlDtoRequest;
import com.url.shortener.dto.UrlDtoResponse;
import com.url.shortener.exception.UrlNotFoundException;

import java.net.MalformedURLException;
import java.util.List;

public interface UrlService {
    String convertToShortUrl(UrlDtoRequest urlDtoRequest) throws MalformedURLException;

    String getOriginalUrl(String shortUrl) throws UrlNotFoundException;

    List<UrlDtoResponse> getAllUrls(int pageNo, int pageSize);
}
