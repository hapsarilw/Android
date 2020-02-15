package com.example.t0615037;

public class Result {
    private String[] result;
    private String error;

    public void setResult(String[] result) {
        this.result = result;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String[] getResult() {
        return result;
    }
}
