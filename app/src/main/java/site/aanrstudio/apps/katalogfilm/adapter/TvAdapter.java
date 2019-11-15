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
import site.aanrstudio.apps.katalogfilm.model.Tv;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {

    private ArrayList<Tv> list = new ArrayList<>();

    public void setData(ArrayList<Tv> items) {
        list.clear();
        list.addAll(items);
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
        final Tv tv = list.get(position);

        holder.txtTitle.setText(tv.getTitle());
        holder.txtYear.setText(tv.getYear());
        holder.txtVote.setText(String.valueOf(tv.getVote()));
        Glide.with(holder.itemView.getContext())
                .load(tv.getPhoto())
                .apply(new RequestOptions().override(185, 185))
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID, tv.getId());
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, "tv");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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
