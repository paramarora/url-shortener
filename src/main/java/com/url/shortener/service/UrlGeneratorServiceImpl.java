package com.url.shortener.service;

import org.springframework.stereotype.Service;

/**
 *
 * This class is used generate unique code
 *
 * @author Param
 */
@Service
public class UrlGeneratorServiceImpl implements UrlGeneratorService{
    private static final char[] arr = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int BASE = 62;


    @Override
    public String encode(long digits) {
        StringBuilder encodedString = new StringBuilder();

        while (digits > 0) {
            encodedString.append(arr[(int) (digits % BASE)]);
            digits = digits / BASE;
        }

        return encodedString.reverse().toString();
    }
}
