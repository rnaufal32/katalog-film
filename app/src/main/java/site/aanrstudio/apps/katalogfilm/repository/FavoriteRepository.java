package site.aanrstudio.apps.katalogfilm.repository;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import site.aanrstudio.apps.katalogfilm.dao.FavoriteDao;
import site.aanrstudio.apps.katalogfilm.db.FavoriteRoomDatabase;
import site.aanrstudio.apps.katalogfilm.model.Favorite;

public class FavoriteRepository {

    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> liveData;

    public Cursor getFav(Context context) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(context);
        this.favoriteDao = db.favoriteDao();
        return this.favoriteDao.selectFav();
    }

    public void setFavTv(Application application) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        this.favoriteDao = db.favoriteDao();
        this.liveData = favoriteDao.getFav("tv");

    }

    public LiveData<List<Favorite>> getFavTv() {
        return liveData;
    }

    public List<String> getPhotos(Context application) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        this.favoriteDao = db.favoriteDao();
        return favoriteDao.getPhotos();
    }

    public void setFavFilm(Application application) {
        FavoriteRoomDatabase db = FavoriteRoomDatabase.getDatabase(application);
        this.favoriteDao = db.favoriteDao();
        this.liveData = favoriteDao.getFav("film");
    }

    public LiveData<List<Favorite>> getFavFilm() {
        return liveData;
    }

    public void insert (Favorite favorite) {
        new insertAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete (int id) {
        new deleteAsyncTask(favoriteDao).execute(id);
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private FavoriteDao mAsyncTaskDao;

        deleteAsyncTask(FavoriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            mAsyncTaskDao.delete(integers[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void> {

        private FavoriteDao mAsyncTaskDao;

        insertAsyncTask(FavoriteDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favorite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
