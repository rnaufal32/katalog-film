package site.aanrstudio.apps.katalogfilm.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import site.aanrstudio.apps.katalogfilm.repository.FavoriteRepository;

public class FavoriteProvider extends ContentProvider {

    private static final String AUTHORITY = "site.aanrstudio.apps.katalogfilm";
    private static final int FAV = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteRepository repository;
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath("tbl_favorite")
            .build();

    static {
        sUriMatcher.addURI(AUTHORITY, "tbl_favorite", FAV);
    }

    @Override
    public boolean onCreate() {
        repository = new FavoriteRepository();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        if (sUriMatcher.match(uri) == FAV) {
            cursor = repository.getFav(getContext());
        } else {
            cursor = null;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
