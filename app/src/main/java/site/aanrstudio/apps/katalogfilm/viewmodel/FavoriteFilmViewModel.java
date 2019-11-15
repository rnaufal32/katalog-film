package site.aanrstudio.apps.katalogfilm.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

import site.aanrstudio.apps.katalogfilm.model.Favorite;
import site.aanrstudio.apps.katalogfilm.repository.FavoriteRepository;

public class FavoriteFilmViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private LiveData<List<Favorite>> liveData;

    public FavoriteFilmViewModel(Application application) {
        super(application);
        repository = new FavoriteRepository();
        repository.setFavFilm(application);
        liveData = repository.getFavFilm();
    }

    public LiveData<List<Favorite>> getLiveData() {
        return liveData;
    }

    public void insert(Favorite favorite) {
        repository.insert(favorite);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
