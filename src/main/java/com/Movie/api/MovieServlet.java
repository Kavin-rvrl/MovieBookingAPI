package com.Movie.api;
import com.Movie.models.Movie;
import com.Movie.service.MovieService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("serial")
@WebServlet(name = "MovieServlet", urlPatterns = {"/movie"})
public class MovieServlet extends HttpServlet {
    private MovieService movieService;
    private Connection connection;
    @Override
    public void init() throws ServletException {
        String jdbcURL = "jdbc:mysql://localhost:3306/Movie";
        String jdbcUsername = "root";
        String jdbcPassword = "Kavin@7010";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            movieService = new MovieService(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Unable to connect to database", e);
        }
    }
    
    
    //to add the movie
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String genre = request.getParameter("genre");
        String director = request.getParameter("director");
        String releaseDate = request.getParameter("release_date");
        String durationParam = request.getParameter("duration");

        if (title == null || title.isEmpty() ||
            description == null || description.isEmpty() ||
            genre == null || genre.isEmpty() ||
            director == null || director.isEmpty() ||
            releaseDate == null || releaseDate.isEmpty() ||
            durationParam == null || durationParam.isEmpty()) {
            out.println("All fields are required");
            return;
        }
        int duration;
        try {
            duration = Integer.parseInt(durationParam);
        } catch (NumberFormatException e) {
            out.println("Invalid duration format");
            return;
        }
        Movie movie = new Movie(0, title, description, genre, director, releaseDate, duration);

        if (movieService.addMovie(movie)) {
            out.println("Movie added successfully");
        } else {
            out.println("Failed to add movie");
        }
    }
    
    //to see the movies 
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        List<Movie> movies = movieService.getAllMovies();
        Gson gson = new Gson();
        String json = gson.toJson(movies);
        out.println(json);
    }

    
    
    //to delete the movie
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String movieIdParam = request.getParameter("movie_id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (movieIdParam == null || movieIdParam.isEmpty() ||
            username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            out.println("All fields are required");
            return;
        }
        
        
        
        

        int movieId;
        try {
            movieId = Integer.parseInt(movieIdParam);
        } catch (NumberFormatException e) {
            out.println("Invalid movie ID");
            return;
        }

       

        if (movieService.deleteMovie(movieId)) {
            out.println("Movie deleted successfully");
        } else {
            out.println("Failed to delete movie");
        }
    }
    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
