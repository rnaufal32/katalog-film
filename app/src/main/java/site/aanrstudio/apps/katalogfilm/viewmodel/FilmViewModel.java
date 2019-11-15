package site.aanrstudio.apps.katalogfilm.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import site.aanrstudio.apps.katalogfilm.BuildConfig;
import site.aanrstudio.apps.katalogfilm.model.Film;

public class FilmViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();

    public LiveData<ArrayList<Film>> getListFilm() {
        return listFilm;
    }

    public void setListFilm() {
        final ArrayList<Film> listItems = new ArrayList<>();

        AndroidNetworking.get(BuildConfig.TMDB_URL+"movie/popular?page=1")
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .addQueryParameter("language", "en")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0;i < jsonArray.length(); i++) {
                                Film film = new Film(jsonArray.getJSONObject(i));
                                listItems.add(film);
                            }

                            listFilm.postValue(listItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }

                });
    }

    public void searchFilm(String query) {
        final ArrayList<Film> listItems = new ArrayList<>();

        AndroidNetworking.get(BuildConfig.TMDB_URL+"search/movie")
                .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                .addQueryParameter("query", query)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0;i < jsonArray.length(); i++) {
                                Film film = new Film(jsonArray.getJSONObject(i));
                                listItems.add(film);
                            }

                            listFilm.postValue(listItems);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }

                });
    }
}
