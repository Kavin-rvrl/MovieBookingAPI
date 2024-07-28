package com.Movie.models;

public class Movie {
    private int movieId;
    private String title;
    private String description;
    private String genre;
    private String director;
    private String releaseDate;
    private int duration;
    public Movie(int movieId, String title, String description, String genre, String director, String releaseDate, int duration) {
        this.movieId = movieId;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.director = director;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public int getMovieId() {
        return movieId;
    }
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public int getDuration() {
        return duration;
    }
  public void setDuration(int duration) {
        this.duration = duration;
    }
}
