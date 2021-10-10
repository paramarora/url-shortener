package com.url.shortener.controller;

import com.url.shortener.dto.UrlDtoRequest;
import com.url.shortener.dto.UrlDtoResponse;
import com.url.shortener.exception.NotCorrectUrlException;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.service.UrlService;
import com.url.shortener.util.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UrlService urlService;


    @Test
    public void convertToShortUrlTest() throws Exception {
        UrlDtoRequest urlDtoRequest = new UrlDtoRequest();
        urlDtoRequest.setLongUrl("http://google.com");

        doReturn("http://localhost/api/a").when(urlService).convertToShortUrl(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create-short-url")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(urlDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(Constant.SUCCESS)));
    }

    @Test
    public void convertToShortUrl_MalformedException_Test() throws Exception {
        UrlDtoRequest urlDtoRequest = new UrlDtoRequest();
        urlDtoRequest.setLongUrl("http://google.com");

        doThrow(new MalformedURLException("Malformed URL")).when(urlService).convertToShortUrl(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create-short-url")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(urlDtoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(Constant.FAILED)));
    }

    @Test
    public void convertToShortUrl_NotCorrectUrlException_Test() throws Exception {
        UrlDtoRequest urlDtoRequest = new UrlDtoRequest();
        urlDtoRequest.setLongUrl("http://google.com");

        doThrow(new NotCorrectUrlException("Url is not correct")).when(urlService).convertToShortUrl(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/create-short-url")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(urlDtoRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(Constant.FAILED)));
    }

    @Test
    public void getOriginalUrlTest() throws Exception {
        doReturn("http://google.com").when(urlService).getOriginalUrl("a");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/{shortUrl}", "a"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void getOriginalUrl_UrlNotFoundException_Test() throws Exception {
        doThrow(new UrlNotFoundException("There is no url with a")).when(urlService).getOriginalUrl("a");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/{shortUrl}", "a"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(Constant.FAILED)));
    }

    @Test
    public void getUrlsTest() throws Exception {
        List<UrlDtoResponse> urlDtoResponses = new ArrayList<>();
        UrlDtoResponse urlDtoResponse = new UrlDtoResponse();
        urlDtoResponse.setShortUrl("http://localhost/a");
        urlDtoResponse.setCount(3);
        urlDtoResponses.add(urlDtoResponse);

        doReturn(urlDtoResponses).when(urlService).getAllUrls(0,10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/urls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].shortUrl", is("http://localhost/a")));

    }

    @Test
    public void getUrls_Exception_Test() throws Exception {
        doThrow(new NullPointerException("Sample Exception")).when(urlService).getAllUrls(0,10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/urls"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(Constant.FAILED)));

    }
}
