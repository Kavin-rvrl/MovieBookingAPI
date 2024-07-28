package com.Movie.service;

import com.Movie.models.Booking;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private Connection connection;

    public BookingService(Connection connection) {
        this.connection = connection;
    }
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Bookings (user_id, movie_id, booking_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getMovieId());
            pstmt.setTimestamp(3, booking.getBookingDate());
            pstmt.setString(4, booking.getStatus());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("movie_id"),
                    rs.getTimestamp("booking_date"),
                    rs.getString("status")
               ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE Bookings SET status = 'canceled' WHERE booking_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
