package com.example.android.demomoviedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.android.demomoviedb.Utilities.NetworkUtils;
import com.example.android.demomoviedb.model.Movie;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    public static RecyclerView mRecyclerView;
    public static MovieAdapter mAdapter;

    private String actionBarTitle;

    public static ArrayList<Movie> movieArrayList = new ArrayList<>();

    public static int currentPage = 1;
    private static Boolean isScrolling = false;
    private static int currentItems, totalItems, scrolledOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView = findViewById(R.id.rv_movies);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new MovieAdapter(movieArrayList, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        actionBarTitle = getString(R.string.most_popular);

        if (isOnlineMainThread()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            getPopularMoviesDetails(currentPage);
        } else {
            noInternetConnection();
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                if (totalItems > 0) {
                    scrolledOutItems = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    if (isScrolling && (currentItems + scrolledOutItems == totalItems)) {
                        isScrolling = false;

                        if (isOnlineMainThread()) {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            getPopularMoviesDetails(currentPage);
                        } else {
                            noInternetConnection();
                        }

                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int selectedItemId = item.getItemId();

        if (selectedItemId == R.id.sort_most_popular) {
            currentPage = 1;
            actionBarTitle = getString(R.string.most_popular);
            setTitle(actionBarTitle);
            movieArrayList.clear();
            mAdapter.notifyDataSetChanged();
            if (isOnlineMainThread()) {
                mRecyclerView.setVisibility(View.VISIBLE);
                getPopularMoviesDetails(currentPage);
            } else {
                noInternetConnection();
            }
        } else if (selectedItemId == R.id.sort_top_rated) {
            currentPage = 1;
            actionBarTitle = getString(R.string.top_rated);
            setTitle(actionBarTitle);
            movieArrayList.clear();
            mAdapter.notifyDataSetChanged();
            if (isOnlineMainThread()) {
                mRecyclerView.setVisibility(View.VISIBLE);
                getTopRatedMoviesDetails(currentPage);
            } else {
                noInternetConnection();
            }
        }
        else if(selectedItemId == R.id.sort_sign_out){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, Authentication.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public static void getPopularMoviesDetails(int pageNumber) {
        URL mostPopularMoviesURL = NetworkUtils.buildUrlPopularMovies(pageNumber);
        new MovieDetails().execute(mostPopularMoviesURL);
    }

    public static void getTopRatedMoviesDetails(int pageNumber) {
        URL topRatedMoviesURL = NetworkUtils.buildUrlTopRatedMovies(pageNumber);
        new MovieDetails().execute(topRatedMoviesURL);
    }

    // Internet connectivity check
    public boolean isOnlineMainThread() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec(getString(R.string.ip_process_command));
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void noInternetConnection() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(Movie clickedMovie) {
        Context context = MainActivity.this;
        Class destinationActivity = DetailActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra(getString(R.string.movie_key), clickedMovie);
        startActivity(intent);
    }
}
