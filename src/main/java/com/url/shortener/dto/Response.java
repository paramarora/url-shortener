package com.url.shortener.dto;

/**
 * This class is used to standardize the API response
 *
 * @author Param
 */
public class Response<T> {
    private String status;
    private String errorMessage;
    private int code;
    private T data;

    public Response() {
    }

    public Response(int code, String status, T data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status=" + status +
                ", errorMessage='" + errorMessage + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
