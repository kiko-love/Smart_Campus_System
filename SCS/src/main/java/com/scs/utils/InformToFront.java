package com.scs.utils;

import java.util.Arrays;
import java.util.List;

public class InformToFront {
    private String status;
    private String code;
    private List<Object> data;

    public InformToFront(String status, String code, List<Object> data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

    public InformToFront() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InformToFront{" +
                "status='" + status + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
