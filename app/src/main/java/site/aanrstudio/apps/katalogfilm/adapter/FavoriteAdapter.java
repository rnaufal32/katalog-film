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

import java.util.List;

import site.aanrstudio.apps.katalogfilm.DetailActivity;
import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.model.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Favorite> favorites;
    private String cat;

    public FavoriteAdapter(String cat) {
        this.cat = cat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Favorite favorite = favorites.get(position);
        holder.txtTitle.setText(favorite.getTITLE());
        holder.txtYear.setText(favorite.getYEAR());
        holder.txtVote.setText(String.valueOf(favorite.getVOTE()));
        Glide.with(holder.itemView.getContext())
                .load(favorite.getPHOTO())
                .apply(new RequestOptions().override(185, 185))
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID, favorite.getId());
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, cat);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favorites != null) {
            return favorites.size();
        } else {
            return 0;
        }
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtYear;
        private TextView txtVote;
        private ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtYear = itemView.findViewById(R.id.txtYear);
            txtVote = itemView.findViewById(R.id.txtVote);
            photo = itemView.findViewById(R.id.photo);
        }
    }
}
