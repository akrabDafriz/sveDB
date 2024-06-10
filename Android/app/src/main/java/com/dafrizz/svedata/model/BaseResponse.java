package com.dafrizz.svedata.model;

public class BaseResponse<T> {
    public boolean success;
    public String message;
    public T payload;
}
