package site.aanrstudio.apps.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import site.aanrstudio.apps.katalogfilm.model.Detail;
import site.aanrstudio.apps.katalogfilm.model.Favorite;
import site.aanrstudio.apps.katalogfilm.viewmodel.DetailViewModel;
import site.aanrstudio.apps.katalogfilm.viewmodel.FavoriteFilmViewModel;
import site.aanrstudio.apps.katalogfilm.viewmodel.FavoriteTvViewModel;
import site.aanrstudio.apps.katalogfilm.widget.FavoriteBannerWidget;

public class DetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_ID = "id";
    private TextView detailTitle;
    private TextView detailYear;
    private TextView detailScore;
    private TextView detailSynopsis;
    private TextView detailVote;
    private ImageView detailPhoto;
    private ImageView detailBgPoster;
    private TextView scroll_title;
    private ProgressBar progressBar;
    private ConstraintLayout constraintLayout;
    private DetailViewModel detailViewModel;
    private FavoriteFilmViewModel favoriteFilmViewModel;
    private FavoriteTvViewModel favoriteTvViewModel;
    private Favorite favorite;
    private boolean check_fav;
    private FloatingActionButton fab;
    private String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        scroll_title = findViewById(R.id.scroll_title);
        scroll_title.setVisibility(View.INVISIBLE);

        final Preferences preferences = new Preferences();

        String lang = preferences.getLang(this);
        if (lang.equals("in")) {
            lang = "id";
        }

        detailTitle = findViewById(R.id.detailTitle);
        detailYear = findViewById(R.id.detailYear);
        detailScore = findViewById(R.id.detailScore);
        detailSynopsis = findViewById(R.id.detailSynopsis);
        detailVote = findViewById(R.id.detailVote);
        detailPhoto = findViewById(R.id.detailPhoto);
        detailBgPoster = findViewById(R.id.detailBgPoster);
        progressBar = findViewById(R.id.detail_progress_bar);
        constraintLayout = findViewById(R.id.detail_view);

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        loading(true);

        cat = getIntent().getStringExtra(EXTRA_CATEGORY);
        if (cat.equals("film")) {
            filmCat(lang, this);
            scroll_title.setText(getResources().getString(R.string.detail_film));
            favoriteFilmViewModel = ViewModelProviders.of(this).get(FavoriteFilmViewModel.class);
        } else if (cat.equals("tv")) {
            tvCat(lang, this);
            scroll_title.setText(getResources().getString(R.string.detail_tv));
            favoriteTvViewModel = ViewModelProviders.of(this).get(FavoriteTvViewModel.class);
        }

        fab = findViewById(R.id.detail_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(view.getContext());
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(view.getContext(), FavoriteBannerWidget.class));
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view);

                final int id = getIntent().getIntExtra(EXTRA_ID, 0);
                if (check_fav) {
                    check_fav = false;
                    fab.setImageResource(R.drawable.ic_star_border_black_24dp);
                    preferences.setFavorite(view.getContext(), id, false);
                    if (cat.equals("film")) {
                        favoriteFilmViewModel.delete(id);
                    } else if (cat.equals("tv")) {
                        favoriteTvViewModel.delete(id);
                    }
                    Snackbar.make(view, getResources().getString(R.string.snack_unfav), Snackbar.LENGTH_LONG)
                            .setAction(getResources().getString(R.string.snack_fav_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    check_fav = true;
                                    fab.setImageResource(R.drawable.ic_star_black_24dp);
                                    preferences.setFavorite(v.getContext(), id, true);
                                    if (cat.equals("film")) {
                                        favoriteFilmViewModel.insert(favorite);
                                    } else if (cat.equals("tv")) {
                                        favoriteTvViewModel.insert(favorite);
                                    }
                                }
                            }).show();
                } else {
                    check_fav = true;
                    fab.setImageResource(R.drawable.ic_star_black_24dp);
                    preferences.setFavorite(view.getContext(), id, true);
                    if (cat.equals("film")) {
                        favoriteFilmViewModel.insert(favorite);
                    } else if (cat.equals("tv")) {
                        favoriteTvViewModel.insert(favorite);
                    }
                    Snackbar.make(view, getResources().getString(R.string.snack_fav), Snackbar.LENGTH_LONG)
                            .setAction(getResources().getString(R.string.snack_fav_cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    check_fav = false;
                                    fab.setImageResource(R.drawable.ic_star_border_black_24dp);
                                    preferences.setFavorite(v.getContext(), id, false);
                                    if (cat.equals("film")) {
                                        favoriteFilmViewModel.delete(id);
                                    } else if (cat.equals("tv")) {
                                        favoriteTvViewModel.delete(id);
                                    }
                                }
                            }).show();
                }
            }
        });
    }

    private void loading(final Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            constraintLayout.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage >= 0.9f) {
            scroll_title.setVisibility(View.VISIBLE);
        } else {
            scroll_title.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private Observer<Detail> details = new Observer<Detail>() {
        @Override
        public void onChanged(Detail detail) {
            loading(false);
            final int id = getIntent().getIntExtra(EXTRA_ID, 0);
            favorite = new Favorite(id, detail.getTitle(), detail.getYear(), detail.getPhoto(), detail.getVote(), cat);
            detailTitle.setText(detail.getTitle());
            detailYear.setText(detail.getYear());
            detailSynopsis.setText(detail.getSynopsis());
            detailScore.setText(String.valueOf(detail.getScore()));
            detailVote.setText(String.valueOf(detail.getVote()));
            setPhoto(DetailActivity.this, detail.getPhoto(), detailPhoto, false);
            setPhoto(DetailActivity.this, detail.getBg_poster(), detailBgPoster, true);
            int size = detail.getTitle().length();
            if (size > 30) {
                detailTitle.setTextSize(15f);
            }
            check_fav = detail.isFavorite();
            if (detail.isFavorite()) {
                fab.setImageResource(R.drawable.ic_star_black_24dp);
            } else {
                fab.setImageResource(R.drawable.ic_star_border_black_24dp);
            }

        }
    };

    private void filmCat(final String lang, Context context) {

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        detailViewModel.setLiveData(id, lang, "movie", context);
        detailViewModel.getLiveData().observe(this, details);

    }

    private void setPhoto(Context context, String source, ImageView imageView, Boolean size) {
        if (size) {
            Glide.with(context)
                    .load(source)
                    .centerCrop()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(source)
                    .apply(new RequestOptions().override(185, 185))
                    .into(imageView);
        }
    }

    private void tvCat(String lang, Context context) {

        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        detailViewModel.setLiveData(id, lang, "tv", context);
        detailViewModel.getLiveData().observe(this, details);

    }
}
