package com.url.shortener.util;

import com.url.shortener.entity.Url;
import com.url.shortener.exception.NotCorrectUrlException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

@RunWith(MockitoJUnitRunner.class)
public class UrlUtilTest {

    @InjectMocks
    UrlUtil urlUtil = new UrlUtil();

    @Mock
    Url url;

    @Test
    public void isValidUrlTest() throws MalformedURLException {
        Assertions.assertTrue(urlUtil.isValidUrl("http://google.com"));
    }

    @Test(expected = NotCorrectUrlException.class)
    public void isValidUrl_NotCorrectUrlException_Test() throws MalformedURLException {
        Assertions.assertTrue(urlUtil.isValidUrl("http://google.comm"));
    }

    @Test(expected = MalformedURLException.class)
    public void isValidUrl_MalformedURLException_Test() throws MalformedURLException {
        Assertions.assertTrue(urlUtil.isValidUrl("http://example.com:-80/"));
    }
}
