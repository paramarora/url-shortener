package com.url.shortener.controller;

import com.url.shortener.dto.Response;
import com.url.shortener.dto.UrlDtoRequest;
import com.url.shortener.dto.UrlDtoResponse;
import com.url.shortener.service.UrlService;
import com.url.shortener.util.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

/**
 * This class handles all URL related requestes
 *
 * @author Param
 *
 */
@RestController
@RequestMapping("/api")
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * This methods handle the post mapping of long to short url conversion
     *
     * @param urlDtoRequest
     * @return Response
     */
    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @PostMapping("create-short-url")
    @ApiResponse( code = 200, message = "Success", response = String.class )
    public ResponseEntity<Response<String>> convertToShortUrl(@RequestBody UrlDtoRequest urlDtoRequest) throws MalformedURLException {
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), Constant.SUCCESS, urlService.convertToShortUrl(urlDtoRequest)));
    }

    /**
     * This methods handle the redirect for short urls
     *
     * @param shortUrl
     * @return void
     */
    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "{shortUrl}")
    public ResponseEntity<Void> getAndRedirect(@PathVariable String shortUrl) {
        String url = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY).header(HttpHeaders.LOCATION, url).build();
    }

    /**
     * This methods return all the short urls and their hit count
     *
     * @param pageNo
     * @param pageSize
     * @return Response
     */

    @ApiOperation(value = "fetch all urls", notes = "Returm all the urls with count")
    @GetMapping(value = "/urls")
    @ApiResponse( code = 200, message = "Success", response = Response.class)
    public ResponseEntity<Response<List<UrlDtoResponse>>> getUrls(@RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), Constant.SUCCESS, urlService.getAllUrls(pageNo, pageSize)));
    }


}
