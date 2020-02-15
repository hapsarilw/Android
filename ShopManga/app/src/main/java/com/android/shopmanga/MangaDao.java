package com.android.shopmanga;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MangaDao {
    @Query("SELECT * FROM manga")
    List<Manga> getAll();

    @Query("SELECT * FROM manga WHERE id = :userIds")
    Manga loadById(String userIds);

    @Insert
    void insertAll(Manga... mangas);

    @Delete
    void delete(Manga manga);
}
