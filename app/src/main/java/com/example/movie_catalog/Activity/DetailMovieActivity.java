package com.example.movie_catalog.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movie_catalog.Database.MovieHelper;
import com.example.movie_catalog.Model.MovieDB.Result;
import com.example.movie_catalog.R;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String DETAIL_MOVIE = "detail_mv";

    private TextView detName;
    private TextView detRate;
    private TextView detDesc;
    private ImageView detPhoto;
    private Result result=new Result();
    private Boolean dbFavorite;
    private Menu favMenu;

    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        detName=findViewById(R.id.mv_name);
        detRate=findViewById(R.id.mv_overview);
        detDesc=findViewById(R.id.mv_desc);
        detPhoto=findViewById(R.id.mv_photo);
        dbFavorite=false;
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please waiting");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(savedInstanceState!=null){
            result=savedInstanceState.getParcelable("ParcelableMovieDetail");
            progressDialog.dismiss();
            showData(result);

        }
        else {
            result = getIntent().getParcelableExtra(DETAIL_MOVIE);
            progressDialog.dismiss();
            showData(result);
        }


        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();


        checkFavorite();



    }

    private void showData(Result result){
        detName.setText(result.getTitle());
        detRate.setText(result.getVoteAverage().toString());
        detDesc.setText(result.getOverview());
//        detPhoto.setImageResource(movie.getPosterPath());
        String path="https://image.tmdb.org/t/p/w185"+result.getPosterPath();
        Glide.with(getApplicationContext())
                .load(path)
                .apply(new RequestOptions().override(200,200))
                .into(detPhoto);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_add_favorite_menu_detail) {
            favoriteButtonPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("ParcelableMovieDetail",result);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        this.favMenu=menu;
        setIconFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    private void setIconFavorite(){
        if (dbFavorite) {
            favMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));

        } else {
            favMenu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }

    private void favoriteButtonPressed(){

        if (dbFavorite) {
            movieHelper.deleteMovie(result.getId());

        } else {
            movieHelper.insertMovie(result);
        }
        dbFavorite = !dbFavorite;
        setIconFavorite();
    }

    private void checkFavorite() {
        ArrayList<Result> resultDb = movieHelper.getAllMovies();


        for (Result results: resultDb){
            Boolean cek=this.result.getId().equals(results.getId()) ;
            Log.i("RESULTNYA",String.valueOf(results.getId()));
            Log.i("RESULTNYA",String.valueOf(cek));
            if (cek){

                dbFavorite = true;
            }
            if ( dbFavorite == true){
                Toast.makeText(this, String.valueOf(dbFavorite), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        movieHelper.close();
    }
}
