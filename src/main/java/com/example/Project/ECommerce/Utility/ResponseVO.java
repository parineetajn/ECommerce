package com.example.Project.ECommerce.Utility;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO {
    private String message;
    private Date timestamp;

    public ResponseVO(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VO{" +
                "message='" + message + '\'' +
                ", date=" + timestamp +
                '}';
    }
}
