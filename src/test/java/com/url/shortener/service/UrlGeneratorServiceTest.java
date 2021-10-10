package com.url.shortener.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UrlGeneratorServiceTest {
    UrlGeneratorService urlGeneratorService = new UrlGeneratorServiceImpl();

    @Test
    public void encodeTest() {
        Assert.assertEquals("b", urlGeneratorService.encode(1));
    }
}
