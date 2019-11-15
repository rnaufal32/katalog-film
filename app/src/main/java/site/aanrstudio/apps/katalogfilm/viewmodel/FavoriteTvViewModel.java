package site.aanrstudio.apps.katalogfilm.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import site.aanrstudio.apps.katalogfilm.model.Favorite;
import site.aanrstudio.apps.katalogfilm.repository.FavoriteRepository;

public class FavoriteTvViewModel extends AndroidViewModel {

    private FavoriteRepository repository;
    private LiveData<List<Favorite>> liveData;

    public FavoriteTvViewModel(Application application) {
        super(application);
        repository = new FavoriteRepository();
        repository.setFavTv(application);
        liveData = repository.getFavTv();
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
