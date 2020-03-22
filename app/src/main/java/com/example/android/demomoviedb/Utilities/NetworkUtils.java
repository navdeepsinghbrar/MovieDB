package com.example.android.demomoviedb.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.demomoviedb.Api.API_KEY;

public class NetworkUtils {

    private final static String MOVIEDB_POPULAR_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/popular";
    private final static String MOVIEDB_TOP_RATED_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated";

    private final static String PARAM_QUERY = "api_key";

    /*How to fetch API Key
    You will need to create an account in themoviedb.org in order to request an API Key.
    In your request for a key, state that your usage will be for educational/non-commercial use.
    You will also need to provide some personal information to complete the request.
    Once you submit your request, you should receive your key via email shortly after.*/

    //Provide your API_KEY here
    //private static String API_KEY = "";


    private final static String PARAM_LANGUAGE = "language";
    private final static String PARAM_PAGE = "page";
    private final static String language = "en-US";

    public static URL buildUrlPopularMovies(int pageNumber) {
        Uri builtUri = Uri.parse(MOVIEDB_POPULAR_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .appendQueryParameter(PARAM_PAGE, String.valueOf(pageNumber))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildUrlTopRatedMovies(int pageNumber) {
        Uri builtUri = Uri.parse(MOVIEDB_TOP_RATED_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .appendQueryParameter(PARAM_LANGUAGE, language)
                .appendQueryParameter(PARAM_PAGE, String.valueOf(pageNumber))
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
