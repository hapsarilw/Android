package com.example.testmangaden.Manga;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MangaList {
    @SerializedName("manga")
    private ArrayList<Manga> list;
    public ArrayList<Manga> getNoticeArrayList() {
        return list;
    }

    public void setNoticeArrayList(ArrayList<Manga> noticeArrayList) {
        this.list = noticeArrayList;
    }
}
