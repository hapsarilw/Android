package com.example.testmangaden.Detail_Comic;

import java.util.List;

public class Chap {

    List<String> list;
    public String getChap()
    {
        return "https://cdn.mangaeden.com/mangasimg/"+list.get(1);
    }
    public String getIndex()
    {
        return list.get(0);
    }

    public Chap(List<String> list) {
        this.list = list;
    }
}
