package site.aanrstudio.apps.katalogfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String[] dataJudul;
    private String[] dataTahun;
    private int[] dataSkor;
    private String[] dataSinop;
    private TypedArray dataFoto;
    private FilmAdapter filmAdapter;
    private ArrayList<Film> films;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filmAdapter = new FilmAdapter(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(filmAdapter);

        prepare();
        add();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film film = new Film();
                film.setJudul(films.get(position).getJudul());
                film.setScore(films.get(position).getScore());
                film.setSinopsis(films.get(position).getSinopsis());
                film.setTahun(films.get(position).getTahun());
                film.setFoto(films.get(position).getFoto());

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FILM, film);
                startActivity(intent);
            }
        });

    }

    private void add() {
        films = new ArrayList<>();

        for (int i = 0;i < dataJudul.length; i++) {
            Film film = new Film();
            film.setJudul(dataJudul[i]);
            film.setTahun(dataTahun[i]);
            film.setFoto(dataFoto.getResourceId(i, -1));
            film.setSinopsis(dataSinop[i]);
            film.setScore(dataSkor[i]);
            films.add(film);
        }

        filmAdapter.setFilms(films);
    }

    private void prepare() {
        dataJudul = getResources().getStringArray(R.array.film_judul);
        dataTahun = getResources().getStringArray(R.array.film_tahun);
        dataFoto = getResources().obtainTypedArray(R.array.film_poster);
        dataSinop = getResources().getStringArray(R.array.film_sinopsis);
        dataSkor = getResources().getIntArray(R.array.film_skor);
    }
}
