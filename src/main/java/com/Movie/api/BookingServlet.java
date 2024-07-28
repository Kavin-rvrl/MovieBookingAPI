package com.Movie.api;
import com.Movie.models.Booking;
import com.Movie.models.User;
import com.Movie.service.BookingService;
import com.Movie.service.MovieService;
import com.Movie.service.UserService;
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
import java.sql.Timestamp;
import java.util.List;
@SuppressWarnings({ "serial" })
@WebServlet(name = "BookingServlet", urlPatterns = {"/booking"})
public class BookingServlet extends HttpServlet {
    private BookingService bookingService;
    private MovieService movieService;
    private UserService userService;
    private Connection connection;
    @Override
    public void init() throws ServletException {
        String jdbcURL = "jdbc:mysql://localhost:3306/Movie";
        String jdbcUsername = "root";
        String jdbcPassword = "Kavin@7010";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            bookingService = new BookingService(connection);
            movieService = new MovieService(connection);
            userService = new UserService(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Unable to connect to database", e);
        }
    }
    
    
    
    
    
    
    @Override
    
    
    
    //for adding the booking 
    
    
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String movieName = request.getParameter("movie_name");
        String bookingDateParam = request.getParameter("booking_date");
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            movieName == null || movieName.isEmpty() ||
            bookingDateParam == null || bookingDateParam.isEmpty()) {
            out.println("All fields are required");
            return;
        }
        Timestamp bookingDate;
        try {
            bookingDate = Timestamp.valueOf(bookingDateParam);
        } catch (IllegalArgumentException e) {
            out.println("Invalid booking date format. Use yyyy-MM-dd HH:mm:ss");
            return;
        }
        User user = userService.getUserByUsernameAndPassword(username, password);

        if (user != null && "user".equals(user.getRole())) {
            Integer movieId = movieService.getMovieIdByName(movieName);
            if (movieId == null) {
                out.println("Movie not found");
                return;
            }
            Booking booking = new Booking(0, user.getUserId(), movieId, bookingDate, "booked");
            if (bookingService.addBooking(booking)) {
                out.println("Booking added successfully");
            } else {
                out.println("Failed to add booking");
            }
        } else {
            out.println("Access denied");
        }
    }
    
    
    
           //to find the total number of movie are booked
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            out.println("Username and password are required");
            return; }
        User user = userService.getUserByUsernameAndPassword(username, password);

        if (user != null && "user".equals(user.getRole())) {
            List<Booking> bookings = bookingService.getBookingsByUserId(user.getUserId());

            
            Gson gson = new Gson();
            String json = gson.toJson(bookings);
            out.println(json);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("{\"message\": \"Access denied\"}");
        }
    }
    
    
    
    //to delete the movie
    
    
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String bookingIdParam = request.getParameter("booking_id");

        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            bookingIdParam == null || bookingIdParam.isEmpty()) {
            out.println("All fields are required");
            return;
        }
        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdParam);
        } catch (NumberFormatException e) {
            out.println("Invalid booking ID");
            return;
        }
        User user = userService.getUserByUsernameAndPassword(username, password);
        if (user != null && "user".equals(user.getRole())) {
            if (bookingService.cancelBooking(bookingId)) {
                out.println("Booking canceled successfully");
            } else {
           out.println("Failed to cancel booking");
            }
        } else {
            out.println("Access denied");
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
