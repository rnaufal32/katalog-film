package site.aanrstudio.apps.katalogfilm.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import site.aanrstudio.apps.katalogfilm.DetailActivity;
import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.model.Film;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private ArrayList<Film> films = new ArrayList<>();

    public void setData(ArrayList<Film> items) {
        films.clear();
        films.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Film film = films.get(position);

        holder.txtTitle.setText(film.getTitle());
        holder.txtYear.setText(film.getYear());
        holder.txtVote.setText(String.valueOf(film.getVote()));
        Glide.with(holder.itemView.getContext())
                .load(film.getPhoto())
                .apply(new RequestOptions().override(185, 185))
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID, film.getId());
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, "film");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtYear;
        private TextView txtVote;
        private ImageView photo;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtVote = itemView.findViewById(R.id.txtVote);
            photo = itemView.findViewById(R.id.photo);
        }

    }
}
