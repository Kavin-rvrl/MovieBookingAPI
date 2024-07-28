package com.Movie.api;

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
import com.Movie.models.User;
import com.Movie.service.UserService;
@SuppressWarnings("serial")
@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
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
            userService = new UserService(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Unable to connect to database", e);
        }
    }
    
    
    //to add the new user or the new admin
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        out.println("Received parameters:");
        out.println("username: " + username);
        out.println("password: " + password);
        out.println("email: " + email);
        out.println("role: " + role);
        if (username == null || password == null || email == null || role == null) {
            out.println("All fields are required");
            return;
        }

  User user = new User(0, username, password, email, role);

        if (userService.addUser(user)) {
            out.println("User added successfully");
        } else {
            out.println("Failed to add user");
        }
    }
    
    
    
    // to see the user or the admin found or there to check 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null && password != null) {
            User user = userService.getUserByUsernameAndPassword(username, password);
            if (user != null) {
                out.println("User found: " + user.getUsername());
            } else {
                out.println("User not found or password incorrect");
            }
        } else {
            out.println("Please provide both username and password");
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
