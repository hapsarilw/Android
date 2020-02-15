package com.example.testmangaden.Detail_Comic;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Detail_Comic implements Serializable {
    @SerializedName("author")
    String author;
    @SerializedName("categories")
    ArrayList<String> cate;
    @SerializedName("chapters")
    ArrayList<List<String>> listchap;
    @SerializedName("created")
    int  date;
    @SerializedName("description")
    String description;
    public Detail_Comic() {
    }

    public ArrayList<List<String>> getListchap() {
        return listchap;
    }

    public void setListchap(ArrayList<List<String>> listchap) {
        this.listchap = listchap;
    }

    public Detail_Comic(String author, ArrayList<String> cate, ArrayList<List<String>> listchap, String description, int date) {
        this.author = author;
        this.cate = cate;
       this.listchap = listchap;
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ArrayList<String> getCate() {
        return cate;
    }

    public void setCate(ArrayList<String> cate) {
        this.cate = cate;
    }


}
