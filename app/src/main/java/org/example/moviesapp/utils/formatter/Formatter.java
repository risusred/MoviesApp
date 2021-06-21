package org.example.moviesapp.utils.formatter;

import androidx.annotation.NonNull;

import org.example.moviesapp.model.Movie;
import org.example.moviesapp.model.MyItem;
import org.example.moviesapp.room.MovieEnty;
import org.example.moviesapp.utils.U;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Formatter implements IFormatter {

    private final String LOG_TAG = getClass().getSimpleName();

    @NonNull
    @Override public List<MyItem> formatDataToListMyItem(@NonNull String dataStringJson) {
        List<Movie> movies = parseJsonToMovies(dataStringJson);
        return transformMoviesToMyItems(movies);
    }
    @NonNull
    @Override public List<MovieEnty> formatDataToListMovieEnty(@NonNull String dataStringJson) {
        List<Movie> movies = parseJsonToMovies(dataStringJson);
        return transformMoviesToMovieEnties(movies);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    private List<MovieEnty> transformMoviesToMovieEnties(@NonNull List<Movie> movies) {
        List<MovieEnty> results = new ArrayList<>();
        int size = movies.size();
        for (int i = 0; i < size; i++) {
            final Movie movie = movies.get(i);
            if (movie != null) {
                MovieEnty myItem = new MovieEnty(
                        movie.getOriginalTitle(),
                        movie.getPosterPath(),
                        movie.getGenre(),
                        movie.getReleaseDate(),
                        movie.getOverview(),
                        movie.getOriginalLanguage());
                results.add(myItem);
            }
        }
        return results;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    private List<MyItem> transformMoviesToMyItems(@NonNull List<Movie> movies) {
        List<MyItem> results = new ArrayList<>();
        int size = movies.size();
        for (int i = 0; i < size; i++) {
            final Movie movie = movies.get(i);
            if (movie != null) {
                MyItem myItem = new MyItem(
                        movie.getOriginalTitle(),
                        movie.getPosterPath(),
                        movie.getGenre(),
                        movie.getReleaseDate(),
                        movie.getOverview(),
                        movie.getOriginalLanguage());
                results.add(myItem);
            }
        }
        return results;
    }

    @NonNull public List<Movie> parseJsonToMovies(String dataStringJson){
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject myJsonObject = new JSONObject(dataStringJson);
            JSONArray resArray = myJsonObject.getJSONArray("results");
            for (int i = 0; i < resArray.length(); i++) {
                JSONObject jsonObject = resArray.getJSONObject(i);

                String originalTitle = jsonObject.getString("original_title");
                String posterPath = jsonObject.getString("poster_path");

                JSONArray genreArry = jsonObject.getJSONArray("genre_ids");
                ArrayList<Integer> genreIds = new ArrayList<>();
                //noinspection ConstantConditions
                if(genreArry != null) {
                    final int length = genreArry.length();
                    for (int j = 0; j < length; j++) {
                        final int genre = (int) genreArry.get(j);
                        genreIds.add(genre);
                    }
                }
                String genre = getGenreIds(genreIds);
                String releaseDate = jsonObject.getString("release_date");
                String overview =jsonObject.getString("overview");
                String originalLanguage = jsonObject.getString("original_language");

                Movie movie = new Movie(
                        originalTitle,
                        posterPath,
                        genre,
                        releaseDate,
                        overview,
                        originalLanguage);

                movies.add(movie);
            }
        } catch (JSONException e) {
            U.le(LOG_TAG,"ko " + e);
        }
        return movies;
    }

    @NonNull public String getGenreIds(ArrayList<Integer> genreIds) {
        StringBuilder result = new StringBuilder();
        if(genreIds != null){
            int size = genreIds.size();
            for (int i = 0; i < size; i++) {
                Integer genre = genreIds.get(i);
                result.append(getGenreHuman(genre)).append("\t");
            }
        }
        return result.toString();
    }

    //https://developers.themoviedb.org/3/genres/get-movie-list
    //https://api.themoviedb.org/3/genre/movie/list?api_key=f321a808e68611f41312aa8408531476&language=en-US
    private String getGenreHuman(int which){
        switch (which) {
            case 28: return "Action";
            case 12: return  "Adventure";
            case 16: return  "Animation";
            case 35: return  "Comedy";
            case 80: return  "Crime";
            case 99: return  "Documentary";
            case 18: return  "Drama";
            case 10751: return  "Family";
            case 14: return  "Fantasy";
            case 36: return  "History";
            case 27: return  "Horror";
            case 10402: return  "Music";
            case 9648: return  "Mystery";
            case 10749: return  "Romance";
            case 878: return  "Science Fiction";
            case 10770: return  "TV Movie";
            case 53: return  "Thriller";
            case 10752: return  "War";
            case 37: return  "Western";
            default: return String.valueOf(which);
        }
    }
}
