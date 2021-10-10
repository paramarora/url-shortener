package com.url.shortener.util;

import com.url.shortener.exception.NotCorrectUrlException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class UrlUtil {

    public boolean isValidUrl(String longUrl) throws MalformedURLException {
        URL url;
        int responseCode = 404;
        try {
            url = new URL(longUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("HEAD");
            responseCode = httpURLConnection.getResponseCode();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("Malformed URL");
        } catch (IOException e) {
            e.printStackTrace();
            throw new NotCorrectUrlException("URL is not correct");
        }
        return responseCode != 404;
    }
}
