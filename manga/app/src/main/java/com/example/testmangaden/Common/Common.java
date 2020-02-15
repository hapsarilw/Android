package com.example.testmangaden.Common;

import com.example.testmangaden.Detail_Comic.Chapter;
import com.example.testmangaden.Detail_Comic.Detail_Comic;
import com.example.testmangaden.Interface.GetNoticeDataService;
import com.example.testmangaden.Manga.Manga;
import com.example.testmangaden.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static Manga selectedManga;
    public  static Detail_Comic selectedComic;
    public static Chapter selectedChapter;
    public static int selectedIndex;
    public static List<Chapter> sourceChapter =new ArrayList<>();
    public static GetNoticeDataService getAPI()
    {

      return  RetrofitInstance.getRetrofitInstance().create(GetNoticeDataService.class);
    }

}
