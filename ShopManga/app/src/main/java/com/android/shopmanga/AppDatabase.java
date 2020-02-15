package com.android.shopmanga;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Manga.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MangaDao mangaDao();
}
