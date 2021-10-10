package com.url.shortener.service;

import com.url.shortener.dto.UrlDtoRequest;
import com.url.shortener.dto.UrlDtoResponse;
import com.url.shortener.entity.Url;
import com.url.shortener.exception.NotCorrectUrlException;
import com.url.shortener.repository.UrlRepository;
import com.url.shortener.util.UrlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @InjectMocks
    UrlService urlService = new UrlServiceImpl();

    @Mock
    UrlRepository urlRepository;

    @Mock
    UrlUtil urlUtil;

    @Spy
    UrlGeneratorService urlGeneratorService;


    @Test
    public void convertToShortUrlTest() throws MalformedURLException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UrlDtoRequest urlDtoRequest = new UrlDtoRequest();
        urlDtoRequest.setLongUrl("http://google.com");

        Url url1 =  new Url();
        url1.setId(1);
        url1.setCount(0);
        url1.setLongUrl("http://google.com");
        url1.setCreatedDate(LocalDate.now());

        Url url2 = new Url();
        url2.setId(1);
        url2.setCount(0);
        url2.setLongUrl("http://google.com");
        url2.setCreatedDate(LocalDate.now());
        url2.setShortUrl("b");
        when(urlRepository.save(any())).thenReturn(url1, url2);
        when(urlUtil.isValidUrl(anyString())).thenReturn(true);

        assertEquals("http://localhost/api/b", urlService.convertToShortUrl(urlDtoRequest));
        Mockito.verify(urlRepository, Mockito.times(2)).save(any());
    }

    @Test(expected = NotCorrectUrlException.class)
    public void convertToShortUrl_Negative_NotCorrectUrlException_Test() throws MalformedURLException {
        UrlDtoRequest urlDtoRequest = new UrlDtoRequest();
        urlDtoRequest.setLongUrl("http://google.com");

        when(urlUtil.isValidUrl(anyString())).thenReturn(false);

        assertEquals("http://localhost/api/b", urlService.convertToShortUrl(urlDtoRequest));
        Mockito.verify(urlRepository, Mockito.times(2)).save(any());
    }

    @Test
    public void getOriginalUrlTest() {
        Url url = new Url();
        url.setLongUrl("http://google.com");
        url.setShortUrl("b");
        url.setCount(2);
        url.setId(1);
        when(urlRepository.findByShortUrl("b")).thenReturn(Optional.of(url));

        Url updatedUrl = new Url();
        updatedUrl.setLongUrl("http://google.com");
        updatedUrl.setShortUrl("b");
        updatedUrl.setCount(3);
        updatedUrl.setId(1);
        when(urlRepository.save(url)).thenReturn(updatedUrl);

        assertEquals("http://google.com", urlService.getOriginalUrl("b"));
    }

    @Test
    public void getAllUrlsTest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        List<Url> urls = new ArrayList<>();
        Url url = new Url();
        url.setLongUrl("http://google.com");
        url.setShortUrl("b");
        url.setCount(2);
        url.setId(1);
        urls.add(url);

        when(urlRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(urls));

        List<UrlDtoResponse> urlDtoResponses = urlService.getAllUrls(0,10);
        assertEquals(1, urlDtoResponses.size());
        verify(urlRepository, times(1)).findAll(any(Pageable.class));
    }
}
