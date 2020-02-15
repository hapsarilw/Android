package com.example.testmangaden.Detail_Comic;

import java.util.List;

public class Chapter {
 List<String> arr;

    public Chapter(Chapter chapter) {
        this.arr=chapter.getArr();
    }

    public String getID()
  {
      return arr.get(3);
  }
  public String getNum() { return arr.get(0); }
  public String getName()
  {
      return arr.get(2);
  }
  public String getDate() {
      return arr.get(1);
  }

    public Chapter( List<String> arr) {
        this.arr = arr;
    }

    public  List<String> getArr() {
        return arr;
    }

    public void setArr( List<String> arr) {
        this.arr = arr;
    }
}
