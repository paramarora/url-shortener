package com.url.shortener.exception;

/**
 *
 * This class is used to throw UrlNotFoundException
 * when their is no url present
 *
 * @author Param
 */
public class UrlNotFoundException extends RuntimeException{
    public UrlNotFoundException(String message) {
        super(message);
    }
}
