package site.aanrstudio.apps.katalogfilm.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.adapter.FavoriteAdapter;
import site.aanrstudio.apps.katalogfilm.model.Favorite;
import site.aanrstudio.apps.katalogfilm.viewmodel.FavoriteFilmViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFilmFragment extends Fragment {


    public FavoriteFilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.fav_film_list);
        final RelativeLayout relativeLayout = view.findViewById(R.id.empty_fav_film);

        final FavoriteAdapter favoriteAdapter = new FavoriteAdapter("film");

        FavoriteFilmViewModel favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        favoriteFilmViewModel.getLiveData().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(List<Favorite> favorites) {
            if (favorites.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            } else {
                favoriteAdapter.setFavorites(favorites);
                recyclerView.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
            }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(favoriteAdapter);
    }
}
