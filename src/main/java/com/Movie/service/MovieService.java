package com.Movie.service;

import com.Movie.models.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class MovieService {
    private Connection connection;

    public MovieService(Connection connection) {
        this.connection = connection;
    }
    public boolean addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, description, genre, director, release_date, duration) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getDescription());
            statement.setString(3, movie.getGenre());
            statement.setString(4, movie.getDirector());
            statement.setString(5, movie.getReleaseDate());
            statement.setInt(6, movie.getDuration());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Movie> getAllMovies() {
        String sql = "SELECT * FROM movies";
        List<Movie> movies = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String genre = resultSet.getString("genre");
                String director = resultSet.getString("director");
                String releaseDate = resultSet.getString("release_date");
                int duration = resultSet.getInt("duration");
                Movie movie = new Movie(movieId, title, description, genre, director, releaseDate, duration);
                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    public boolean deleteMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	public Integer getMovieIdByName(String movieName) {
		return null;
	}
}
