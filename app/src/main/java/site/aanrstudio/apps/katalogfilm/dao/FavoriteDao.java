package site.aanrstudio.apps.katalogfilm.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import site.aanrstudio.apps.katalogfilm.model.Favorite;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Query("DELETE FROM tbl_favorite WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM tbl_favorite WHERE category = :cat")
    LiveData<List<Favorite>> getFav(String cat);

    @Query("SELECT photo FROM tbl_favorite")
    List<String> getPhotos();

    @Query("SELECT * FROM tbl_favorite")
    Cursor selectFav();

}
