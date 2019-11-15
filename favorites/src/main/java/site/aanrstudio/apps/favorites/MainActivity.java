package site.aanrstudio.apps.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static site.aanrstudio.apps.favorites.DatabaseFavorite.FavoriteColumns.CONTENT_URI;
import static site.aanrstudio.apps.favorites.DatabaseFavorite.curToArray;

public class MainActivity extends AppCompatActivity implements FavoriteCallback{

    private RecyclerView recyclerView;
    private Adapter adapter;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new getData(this, this).execute();

        recyclerView = findViewById(R.id.rc_fav);
        relativeLayout = findViewById(R.id.empty_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public void postExecute(Cursor fav) {
        ArrayList<Favorite> favorites = curToArray(fav);
        if (favorites.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            adapter = new Adapter(favorites);
            recyclerView.setAdapter(adapter);
        }
    }

    private static class getData extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<FavoriteCallback> weakReference;
        private getData(Context context, FavoriteCallback callback) {
            weakContext = new WeakReference<>(context);
            weakReference = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor data) {
            super.onPostExecute(data);
            weakReference.get().postExecute(data);
        }
    }
}
