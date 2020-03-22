package com.example.android.demomoviedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.demomoviedb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    public static ArrayList<Movie> mMovies;
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public final static String IMAGE_BASE_URL_SIZE = "w185";

    public MovieAdapter(ArrayList<Movie> movies, MovieAdapterOnClickHandler mClickHandler) {
        this.mMovies = movies;
        this.mClickHandler = mClickHandler;
    }

    public static MovieAdapterOnClickHandler mClickHandler;

    interface MovieAdapterOnClickHandler {
        void onClick(Movie clickedMovie);

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String imageUrl = IMAGE_BASE_URL + IMAGE_BASE_URL_SIZE + mMovies.get(position).getPosterPath();

        Picasso.get()
                .load(imageUrl.isEmpty() ? null : imageUrl)
                .placeholder(R.drawable.blank)
                .error(R.drawable.image_not_available)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }
}
