package site.aanrstudio.apps.katalogfilm.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import site.aanrstudio.apps.katalogfilm.dao.FavoriteDao;
import site.aanrstudio.apps.katalogfilm.model.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteRoomDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static volatile FavoriteRoomDatabase INSTANSCE;

    public static FavoriteRoomDatabase getDatabase(final Context context) {
        if (INSTANSCE == null) {
            synchronized (FavoriteRoomDatabase.class) {
                if (INSTANSCE == null) {
                    INSTANSCE = Room.databaseBuilder(context.getApplicationContext(), FavoriteRoomDatabase.class, "db_catalogue")
                            .build();
                }
            }
        }
        return INSTANSCE;
    }
}
