package com.example.testmangaden.Detail_Comic;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class detail_chap {
    @SerializedName("images")
    List<List<String>> list;

    public detail_chap(List<List<String>> list) {
        this.list = list;
    }

    public List<Chap> getList()
    {
        List<Chap> result= new ArrayList<>();
        for(int i=list.size()-1;i>=0;i--)
        {
            result.add(new Chap(list.get(i)));
        }
        return result;

    }

    public void setList(List<List<String>> list ) {
        this.list = list;
    }
}
