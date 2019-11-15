package site.aanrstudio.apps.katalogfilm.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_favorite")
public class Favorite {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String TITLE;

    @NonNull
    @ColumnInfo(name = "year")
    private String YEAR;

    @NonNull
    @ColumnInfo(name = "photo")
    private String PHOTO;

    @NonNull
    @ColumnInfo(name = "vote")
    private double VOTE;

    @NonNull
    @ColumnInfo(name = "category")
    private String CATEGORY;

    public Favorite(int id, @NonNull String TITLE, @NonNull String YEAR, @NonNull String PHOTO, @NonNull double VOTE, @NonNull String CATEGORY) {
        this.id = id;
        this.TITLE = TITLE;
        this.YEAR = YEAR;
        this.PHOTO = PHOTO;
        this.VOTE = VOTE;
        this.CATEGORY = CATEGORY;
    }

    @NonNull
    public String getCATEGORY() {
        return CATEGORY;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTITLE() {
        return TITLE;
    }

    @NonNull
    public String getYEAR() {
        return YEAR;
    }

    @NonNull
    public String getPHOTO() {
        return PHOTO;
    }

    @NonNull
    public double getVOTE() {
        return VOTE;
    }
}
