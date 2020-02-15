package com.example.testmangaden.Manga;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Manga implements Serializable {
    @SerializedName("a")
    private String a;
    @SerializedName("c")
    private ArrayList<String> c;
    @SerializedName("h")
    private String h;
    @SerializedName("i")
    private String i;
    @SerializedName("im")
    private String im;
    @SerializedName("ld")
    private String ld;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public ArrayList<String> getC() {
        return c;
    }

    public void setC(ArrayList<String> c) {
        this.c = c;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public String getIm() {
        return "https://cdn.mangaeden.com/mangasimg/"+im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Manga(String a, ArrayList<String> c, String h, String i, String im, String ld, String s, String t) {
        this.a = a;
        this.c = c;
        this.h = h;
        this.i = i;
        this.im = im;
        this.ld = ld;
        this.s = s;
        this.t = t;
    }

    @SerializedName("s")
    private String s;
    @SerializedName("t")
    private String t;
}
