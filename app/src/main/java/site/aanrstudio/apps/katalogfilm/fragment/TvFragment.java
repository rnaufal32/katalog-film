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

import site.aanrstudio.apps.katalogfilm.R;
import site.aanrstudio.apps.katalogfilm.adapter.TvAdapter;
import site.aanrstudio.apps.katalogfilm.model.Tv;
import site.aanrstudio.apps.katalogfilm.viewmodel.TvViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    private TvAdapter tvAdapter;
    private ArrayList<Tv> tvs;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.tv_progress_bar);

        AndroidNetworking.initialize(view.getContext());

        TvViewModel tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.setLiveData();
        tvViewModel.getLiveData().observe(this, getTv);

        tvAdapter = new TvAdapter();
        tvAdapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.tv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(tvAdapter);

        loading(true);
    }

    private Observer<ArrayList<Tv>> getTv = new Observer<ArrayList<Tv>>() {
        @Override
        public void onChanged(ArrayList<Tv> tvs) {
            if (tvs != null) {
                tvAdapter.setData(tvs);
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
