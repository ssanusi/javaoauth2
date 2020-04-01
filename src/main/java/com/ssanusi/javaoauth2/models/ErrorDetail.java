package com.ssanusi.javaoauth2.models;

import com.ssanusi.javaoauth2.exceptions.ValidationError;
import com.ssanusi.javaoauth2.logging.Loggable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Loggable
public class ErrorDetail {

    private String tittle;
    private int status;
    private String detail;
    private String timestamp;
    private String developerMessage;
    private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = new SimpleDateFormat("dd MMM yyy HH:mm:ss:SSS Z").format(new Date(timestamp));
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Map<String, List<ValidationError>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "tittle='" + tittle + '\'' +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", errors=" + errors +
                '}';
    }
}
