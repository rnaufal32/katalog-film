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
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import java.util.ArrayList;

import site.aanrstudio.apps.katalogfilm.model.Film;
import site.aanrstudio.apps.katalogfilm.adapter.FilmAdapter;
import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.viewmodel.FilmViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment {

    private FilmAdapter filmAdapter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.movie_progress_bar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        AndroidNetworking.initialize(view.getContext());

        FilmViewModel filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        filmViewModel.setListFilm();
        filmViewModel.getListFilm().observe(this, getFilm);

        filmAdapter = new FilmAdapter();
        filmAdapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(filmAdapter);

        loading(true);

    }

    private Observer<ArrayList<Film>> getFilm = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null) {
                filmAdapter.setData(films);
                loading(false);
            }
        }
    };

    private void loading (Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

}
