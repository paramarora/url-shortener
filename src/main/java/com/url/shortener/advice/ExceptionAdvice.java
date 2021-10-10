package com.url.shortener.advice;

import com.url.shortener.util.Constant;
import com.url.shortener.exception.NotCorrectUrlException;
import com.url.shortener.exception.UrlNotFoundException;
import com.url.shortener.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.MalformedURLException;

/**
 * This class is used to handle all exceptions which are thrown during
 * execution.
 *
 * @author Param
 *
 */
@ControllerAdvice
public class ExceptionAdvice {
    /**
     * This method handles UrlNotFoundException
     * @param UrlNotFoundException
     * @return Response class
     */
    @ExceptionHandler(value = UrlNotFoundException.class)
    public ResponseEntity<Response<Object>> exception(UrlNotFoundException e) {
        Response<Object> response = new Response<>();
        response.setStatus(Constant.FAILED);
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles NotCorrectUrlException
     * @param NotCorrectUrlException
     * @return Response class
     */
    @ExceptionHandler(value = NotCorrectUrlException.class)
    public ResponseEntity<Object> exception(NotCorrectUrlException e) {
        Response<Object> response = new Response<>();
        response.setStatus(Constant.FAILED);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles MalformedURLException
     * @param MalformedURLException
     * @return Response class
     */
    @ExceptionHandler(value = MalformedURLException.class)
    public ResponseEntity<Object> exception(MalformedURLException e) {
        Response<Object> response = new Response<>();
        response.setStatus(Constant.FAILED);
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles Global Level Exceptions
     * @param Exception
     * @return Response class
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> exception(Exception e) {
        Response<Object> response = new Response<>();
        response.setStatus(Constant.FAILED);
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setErrorMessage("Server Error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
