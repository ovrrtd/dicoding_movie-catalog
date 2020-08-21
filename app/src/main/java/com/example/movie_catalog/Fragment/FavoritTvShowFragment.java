package com.example.movie_catalog.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movie_catalog.Activity.DetailTvActivity;
import com.example.movie_catalog.Adapter.TvShowDbAdapter;
import com.example.movie_catalog.Database.TvShowHelper;
import com.example.movie_catalog.Model.TvShowDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritTvShowFragment extends Fragment {

    private ArrayList<Result> listDb=new ArrayList<>();

    private RecyclerView rvMoviesDb;

    private TvShowDbAdapter tvShowDbAdapter;

    private TvShowHelper tvShowHelper;

    public FavoritTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favorit_tv_show, container, false);
        rvMoviesDb = view.findViewById(R.id.rv_tvShow_fav);
        rvMoviesDb.setHasFixedSize(true);

        tvShowHelper = tvShowHelper.getInstance(view.getContext());
        tvShowHelper.open();

        if (savedInstanceState != null) {
            listDb = savedInstanceState.getParcelableArrayList("ParcelableTV");
            showRecyclerListDb();
        }
        else {
            prepareDB();
        }
        // Inflate the layout for this fragment
        return view;
    }


    private void prepareDB(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("It's loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        listDb.addAll(tvShowHelper.getAllTvShows());
        showRecyclerListDb();
        progressDialog.dismiss();

    }
    private void showRecyclerListDb(){
        rvMoviesDb.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowDbAdapter = new TvShowDbAdapter(listDb);
        rvMoviesDb.setAdapter(tvShowDbAdapter);

        tvShowDbAdapter.setOnItemClickCallback(new TvShowDbAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Result result) {
                Toast.makeText(getContext(), result.getOriginalName(), Toast.LENGTH_SHORT).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailTvActivity.class);
                moveWithObjectIntent.putExtra(DetailTvActivity.DETAIL_TV, result);
                startActivity(moveWithObjectIntent);
            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("ParcelableTV",listDb);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        listDb = tvShowHelper.getAllTvShows();
        tvShowDbAdapter.setData(listDb);
    }

}
