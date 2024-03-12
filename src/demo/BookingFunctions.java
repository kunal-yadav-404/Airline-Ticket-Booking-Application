package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingFunctions {
	//	1. bookTicket
	
	public static void bookTicket(Connection connection) {
	    try {
	        connection.setAutoCommit(false); // Start a transaction

	        // Get passenger and flight information from user input
	        System.out.print("Enter Passenger ID: ");
	        int passengerId = new Scanner(System.in).nextInt();

	        System.out.print("Enter Flight Number: ");
	        int flightNumber = new Scanner(System.in).nextInt();

	        // Check if there are available seats on the selected flight
	        int availableSeats = getAvailableSeats(connection, flightNumber);
	        if (availableSeats > 0) {
	            // Update the bookedSeats in the flights table
	            updateBookedSeats(connection, flightNumber, availableSeats - 1);

	            // Insert a record in the bookings table
	            insertBooking(connection, passengerId, flightNumber);

	            connection.commit(); // Commit the transaction

	            System.out.println("Ticket booked successfully!");
	        } else {
	            System.out.println("Sorry, no available seats on the selected flight.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            connection.rollback(); // Rollback the transaction in case of an exception
	            System.out.println("Transaction rolled back.");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        try {
	            connection.setAutoCommit(true); // Restore auto-commit mode
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private static int getAvailableSeats(Connection connection, int flightNumber) throws SQLException {
	    String query = "SELECT availableSeats FROM flights WHERE flightNumber = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, flightNumber);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt("availableSeats");
	            }
	        }
	    }
	    return 0;
	}

	private static void updateBookedSeats(Connection connection, int flightNumber, int newAvailableSeats) throws SQLException {
	    String query = "UPDATE flights SET availableSeats = ? WHERE flightNumber = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, newAvailableSeats);
	        preparedStatement.setInt(2, flightNumber);
	        preparedStatement.executeUpdate();
	    }
	}

	private static void insertBooking(Connection connection, int passengerId, int flightNumber) throws SQLException {
	    String query = "INSERT INTO bookings (passengerId, flightNumber) VALUES (?, ?)";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, passengerId);
	        preparedStatement.setInt(2, flightNumber);
	        preparedStatement.executeUpdate();
	    }
	}
	
	
	//	2. cancelTicket
	
	public static void cancelTicket(Connection connection) {
	    try {
	        connection.setAutoCommit(false); // Start a transaction

	        // Sample logic to get user input for Booking ID to cancel
	        System.out.print("Enter Booking ID to cancel: ");
	        int bookingId = new Scanner(System.in).nextInt();

	        // Fetch the Flight Number and Passenger ID associated with the booking
	        int flightNumber = getFlightNumberForBooking(connection, bookingId);
	        int passengerId = getPassengerIdForBooking(connection, bookingId);

	        // Update the bookedSeats in the flights table
	        int availableSeats = getAvailableSeats(connection, flightNumber);
	        updateBookedSeats(connection, flightNumber, availableSeats + 1);

	        // Delete the record from the bookings table
	        deleteBooking(connection, bookingId);

	        connection.commit(); // Commit the transaction
	        System.out.println("Ticket canceled successfully!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	            connection.rollback(); // Rollback the transaction in case of an exception
	            System.out.println("Transaction rolled back.");
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        try {
	            connection.setAutoCommit(true); // Restore auto-commit mode
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private static int getFlightNumberForBooking(Connection connection, int bookingId) throws SQLException {
	    String query = "SELECT flightNumber FROM bookings WHERE bookingId = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, bookingId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt("flightNumber");
	            }
	        }
	    }
	    return 0;
	}

	private static int getPassengerIdForBooking(Connection connection, int bookingId) throws SQLException {
	    String query = "SELECT passengerId FROM bookings WHERE bookingId = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, bookingId);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                return resultSet.getInt("passengerId");
	            }
	        }
	    }
	    return 0;
	}

	private static void deleteBooking(Connection connection, int bookingId) throws SQLException {
	    String query = "DELETE FROM bookings WHERE bookingId = ?";
	    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        preparedStatement.setInt(1, bookingId);
	        preparedStatement.executeUpdate();
	    }
	}
}
