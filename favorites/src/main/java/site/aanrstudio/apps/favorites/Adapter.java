package site.aanrstudio.apps.favorites;

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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Favorite> favorites;

    public Adapter(ArrayList<Favorite> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favorite favorite = favorites.get(position);
        holder.txtTitle.setText(favorite.getTITLE());
        holder.txtDate.setText(favorite.getYEAR());
        Glide.with(holder.itemView.getContext())
                .load(favorite.getPHOTO())
                .apply(new RequestOptions().override(185, 185))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (favorites.size() > 0) {
            return favorites.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtTitle;
        private TextView txtDate;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photos);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
        }
    }
}
