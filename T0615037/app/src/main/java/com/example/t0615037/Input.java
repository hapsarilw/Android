package com.example.t0615037;

public class Input {
    protected String[] expr;
    protected int precision;

    Input(String[] expr, int precision){
        this.expr = expr;
        this.precision = precision;
    }


    public void setEkspresi(String[] ekspresi) {
        this.expr = ekspresi;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }

    public String[] getEkspresi() {
        return expr;
    }
}
