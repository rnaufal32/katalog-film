package site.aanrstudio.apps.katalogfilm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.ref.WeakReference;

import site.aanrstudio.apps.katalogfilm.fragment.FilmFragment;
import site.aanrstudio.apps.katalogfilm.fragment.TvFragment;
import site.aanrstudio.apps.katalogfilm.notification.AlarmNotification;

import static site.aanrstudio.apps.katalogfilm.provider.FavoriteProvider.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FilmFragment filmFragment = new FilmFragment();
    private TvFragment tvFragment = new TvFragment();
    private Preferences preferences = new Preferences();
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmNotification alarmNotification = new AlarmNotification();
        boolean notif_user = preferences.getNotifUser(this);
        boolean notif_new = preferences.getNotifNew(this);

        if (notif_user) {
            alarmNotification.cancelAlarmUser(this);
            alarmNotification.setAlarmUser(this, "07:00");
        }

        if (notif_new) {
            alarmNotification.cancelAlarmNew(this);
            alarmNotification.setAlarmNew(this, "08:00");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        preferences.setLocale(this, bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_movie:
                        category = "movie";
                        loadFragment(filmFragment);
                        return true;
                    case R.id.menu_tv:
                        category = "tv";
                        loadFragment(tvFragment);
                        return true;
                    case R.id.menu_fav:
                        startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                        return false;
                    default:
                        return true;
                }
            }
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.menu_movie);
            new LoadNoteAsync(this).execute();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                return true;
            case R.id.menu_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.CATEGORY, category);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction tvTransaction = fragmentManager.beginTransaction();
        tvTransaction.replace(R.id.fragment_container, fragment);
        tvTransaction.commit();
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;

        private LoadNoteAsync(Context context) {
            weakContext = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favs) {
            super.onPostExecute(favs);
        }
    }
}
