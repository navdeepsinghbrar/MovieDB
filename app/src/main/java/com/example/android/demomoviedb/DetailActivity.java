package com.example.android.demomoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.demomoviedb.databinding.ActivityDetailBinding;
import com.example.android.demomoviedb.model.Movie;
import com.squareup.picasso.Picasso;

import static com.example.android.demomoviedb.MovieAdapter.IMAGE_BASE_URL;
import static com.example.android.demomoviedb.MovieAdapter.IMAGE_BASE_URL_SIZE;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();
        Movie movie = intentThatStartedThisActivity.getParcelableExtra(getString(R.string.movie_key));

        if (movie != null) {
            String imageUrl = IMAGE_BASE_URL + IMAGE_BASE_URL_SIZE + movie.getPosterPath();
            String title = movie.getOriginalTitle();
            int rating = movie.getVoteAverage();
            String releaseDate = movie.getReleaseDate();
            String overview = movie.getOverview();

            Picasso.get()
                    .load(imageUrl.isEmpty() ? null : imageUrl)
                    .placeholder(R.drawable.blank)
                    .error(R.drawable.image_not_available)
                    .into(mBinding.imageViewMovieThumbnail);

            mBinding.textViewMovieTitle.setText(title);
            mBinding.textViewMovieRating.setText(String.valueOf(rating));
            mBinding.textViewMovieReleaseDate.setText(releaseDate);
            mBinding.textViewMovieOverview.setText(overview);

            setTitle(title);

        } else
            Toast.makeText(getApplicationContext(), getString(R.string.some_error_occurred), Toast.LENGTH_SHORT).show();


    }
}
