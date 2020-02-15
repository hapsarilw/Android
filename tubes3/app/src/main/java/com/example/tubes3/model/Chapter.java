package com.example.tubes3.model;

import java.util.ArrayList;

public class Chapter {
    String title;
    int chapters_len;
    ArrayList<String> categories;
    String author;
    String artist;
    String image;
    String description;
    int released;
    ArrayList<Object> chapters;
    public Chapter(){

    }

    public String getDescription() {
        return description;
    }


    public String getImage() {
        return image;
    }


    public String getTitle() {
        return title;
    }


    public int getChapters_len() {
        return chapters_len;
    }


    public ArrayList<String> getCategories() {
        return categories;
    }


    public String getAuthor() {
        return author;
    }


    public String getArtist() {
        return artist;
    }


    public int getReleased() {
        return released;
    }


    public ArrayList<Object> getChapters() {
        return chapters;
    }

}
