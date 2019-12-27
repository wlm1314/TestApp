package com.wang.myapplication.entity;

import java.util.List;

public class ResultBean {

    /**
     * reason : 查询成功
     * result : [{"id":"82","province_id":"2","city_name":"澳门"}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<Result> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {
        /**
         * id : 82
         * province_id : 2
         * city_name : 澳门
         */

        private String id;
        private String province_id;
        private String city_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
