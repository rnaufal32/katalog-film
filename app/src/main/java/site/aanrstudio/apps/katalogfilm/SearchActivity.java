package site.aanrstudio.apps.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import site.aanrstudio.apps.katalogfilm.adapter.FilmAdapter;
import site.aanrstudio.apps.katalogfilm.adapter.TvAdapter;
import site.aanrstudio.apps.katalogfilm.model.Film;
import site.aanrstudio.apps.katalogfilm.model.Tv;
import site.aanrstudio.apps.katalogfilm.viewmodel.FilmViewModel;
import site.aanrstudio.apps.katalogfilm.viewmodel.TvViewModel;

public class SearchActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private String querys;
    private FilmAdapter filmAdapter;
    private FilmViewModel filmViewModel;
    private TvViewModel tvViewModel;
    private TvAdapter tvAdapter;
    public static String CATEGORY = "category";
    private String cat;

    private Runnable finds = new Runnable() {
        @Override
        public void run() {
            if (cat.equals("movie")) {
                filmViewModel.searchFilm(querys);
            } else {
                tvViewModel.searchTv(querys);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        filmViewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);


        filmAdapter = new FilmAdapter();
        tvAdapter = new TvAdapter();


        RecyclerView recyclerView = findViewById(R.id.search_list);
        recyclerView.setLayoutManager(mLayoutManager);

        cat = getIntent().getStringExtra(CATEGORY);
        if (cat.equals("movie")) {
            filmViewModel.getListFilm().observe(this, getFilm);
            filmAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(filmAdapter);
        } else if (cat.equals("tv")) {
            tvViewModel.getLiveData().observe(this, getTv);
            tvAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(tvAdapter);
        }
    }

    private Observer<ArrayList<Tv>> getTv = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tvs) {
            if (tvs != null) {
                tvAdapter.setData(tvs);
            }
        }
    };

    private Observer<ArrayList<Film>> getFilm = new Observer<ArrayList<Film>>() {
        @Override
        public void onChanged(ArrayList<Film> films) {
            if (films != null) {
                filmAdapter.setData(films);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_search, menu);

        MenuItem menu_search = menu.findItem(R.id.menu_search);
        menu_search.expandActionView();
        menu_search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu_search.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handler.removeCallbacks(finds);
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                querys = newText;
                handler.postDelayed(finds, 1000);
                return true;
            }
        });
        return true;
    }
}
