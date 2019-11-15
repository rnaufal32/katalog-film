package site.aanrstudio.apps.favorites;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

public class DatabaseFavorite {

    private static final String AUTHORITY = "site.aanrstudio.apps.katalogfilm";
    private static final String SCHEME = "content";
    private static final String TABLE_NAME = "tbl_favorite";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String YEAR = "year";
    private static final String PHOTO = "photo";
    private static final String VOTE = "photo";
    private static final String CATEGORY = "photo";

    static final class FavoriteColumns implements BaseColumns {
        static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static ArrayList<Favorite> curToArray(Cursor fav) {
        ArrayList<Favorite> favorites = new ArrayList<>();
        while (fav.moveToNext()) {
            int id = fav.getInt(fav.getColumnIndexOrThrow(ID));
            String title = fav.getString(fav.getColumnIndexOrThrow(TITLE));
            String year = fav.getString(fav.getColumnIndexOrThrow(YEAR));
            String photo = fav.getString(fav.getColumnIndexOrThrow(PHOTO));
            double vote = fav.getDouble(fav.getColumnIndexOrThrow(VOTE));
            String cat = fav.getString(fav.getColumnIndexOrThrow(CATEGORY));
            favorites.add(new Favorite(id, title, year, photo, vote, cat));
        }
        return favorites;
    }

}
