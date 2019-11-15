package site.aanrstudio.apps.favorites;

import android.database.Cursor;

interface FavoriteCallback {
    void postExecute(Cursor fav);
}
