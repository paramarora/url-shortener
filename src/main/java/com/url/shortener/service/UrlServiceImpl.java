package com.url.shortener.service;

import com.url.shortener.dto.UrlDtoRequest;
import com.url.shortener.dto.UrlDtoResponse;
import com.url.shortener.entity.Url;
import com.url.shortener.exception.NotCorrectUrlException;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.repository.UrlRepository;
import com.url.shortener.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class is used to handle all the buiness rules related to Url Entity
 *
 * @author Param
 */
@Service
public class UrlServiceImpl implements UrlService{

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlGeneratorService urlGeneratorService;

    @Autowired
    private UrlUtil urlUtil;

    /**
     * This method is used to validate the Long Url and return a created url after saving it in DB
     *
     * @param UrlDtoRequest contains long Url
     * @return String type short Url
     * @throws Throwable throws NotCorrectUrlException, MalformedURLException
     */
    @Override
    public String convertToShortUrl(UrlDtoRequest urlDtoRequest) throws MalformedURLException {
        String longUrl = urlDtoRequest.getLongUrl();

        if(urlUtil.isValidUrl(longUrl)){
            Url urlEntity = new Url();
            urlEntity.setLongUrl(urlDtoRequest.getLongUrl());
            urlEntity.setCreatedDate(LocalDate.now());
            urlEntity.setCount(0);
            urlEntity = urlRepository.save(urlEntity);
            urlEntity.setShortUrl(urlGeneratorService.encode(urlEntity.getId()));
            urlEntity = urlRepository.save(urlEntity);
            final String baseUrl =
                    ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return baseUrl + "/api/" + urlEntity.getShortUrl();
        } else {
            throw new NotCorrectUrlException("Url is not accessable");
        }
    }

    /**
     * This method is used to get orignal url from the short url
     *
     * @param String type short Url
     * @return String type long Url
     * @throws Throwable throws UrlNotFoundException
     */
    @Override
    public String getOriginalUrl(String shortUrl) throws UrlNotFoundException {
        Url url = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("There is no url with " + shortUrl));
        url.setCount(url.getCount() + 1);
        url = urlRepository.save(url);
        return url.getLongUrl();
    }

    /**
     * This method is used to fetch all the Short Urls record with count
     * @param pageNo
     * @param pageSize
     * @return List type of UrlDtoResponse
     */
    @Override
    public List<UrlDtoResponse> getAllUrls(int pageNo, int pageSize) {
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        List<UrlDtoResponse> urlDtoResponses = new ArrayList<>();

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Url> pagedResult = urlRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            List<Url> urls = pagedResult.getContent();
            for (Url url : urls) {
                UrlDtoResponse urlDtoResponse = new UrlDtoResponse();
                urlDtoResponse.setCount(url.getCount());
                urlDtoResponse.setShortUrl(baseUrl + "/api/" +url.getShortUrl());
                urlDtoResponses.add(urlDtoResponse);
            }
        }
        return urlDtoResponses;
    }
}
