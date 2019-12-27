package com.wang.myapplication.entity;

public class RequestBean {
    private String province_id;
    private String key;

    public RequestBean(String province_id, String key) {
        this.key = key;
        this.province_id = province_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }
}
