package org.example.moviesapp.model;



import androidx.annotation.Nullable;

public class Movie {

    @Nullable private final String originalTitle;
    @Nullable private final String posterPath;
    @Nullable private final String genre;
    @Nullable private final String releaseDate;
    @Nullable private final String overview;
    @Nullable private final String originalLanguage;

    public Movie(@Nullable String originalTitle,
                 @Nullable String posterPath,
                 @Nullable String genre,
                 @Nullable String releaseDate,
                 @Nullable String overview,
                 @Nullable String originalLanguage) {

        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
    }

    @Nullable public String getOriginalTitle() { return originalTitle; }

    @Nullable public String getReleaseDate() { return releaseDate; }

    @Nullable public String getPosterPath() {
        if (posterPath != null && !posterPath.isEmpty()) {

            if(!posterPath.toLowerCase().contains("https://")){
                return "https://image.tmdb.org/t/p/w342" + posterPath;
            }else{
                return posterPath;
            }

        }
        return null;
    }

    @Nullable public String getGenre() { return genre; }

    @Nullable public String getOverview() { return overview; }

    @Nullable public String getOriginalLanguage() { return originalLanguage; }

}
