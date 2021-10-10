package com.url.shortener.exception;

/**
*
* This class is used to throw NotCorrectUrlException
* when their is error in url or url is not correct
*
* @author Param
 */
public class NotCorrectUrlException extends RuntimeException{
    public NotCorrectUrlException(String message) {
        super(message);
    }
}
