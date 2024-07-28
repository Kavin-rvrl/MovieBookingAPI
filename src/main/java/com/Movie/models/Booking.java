package com.Movie.models;
import java.sql.Timestamp;
public class Booking {
    private int bookingId;
    private int userId;
    private int movieId;
    private Timestamp bookingDate;
    private String status;

    public Booking() {}
    public Booking(int bookingId, int userId, int movieId, Timestamp bookingDate, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.bookingDate = bookingDate;
        this.status = status;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
