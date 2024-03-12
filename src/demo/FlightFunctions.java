package demo;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FlightFunctions {
	public static void insertFlightDetails(Connection connection) {
	    try {
	        // Get flight details from user input or any other source
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter Flight Number: ");
	        int flightNumber = scanner.nextInt();

	        scanner.nextLine(); // Consume the newline character

	        System.out.print("Enter Airline: ");
	        String airline = scanner.nextLine();

	        System.out.print("Enter Total Seats: ");
	        int totalSeats = scanner.nextInt();

	        System.out.print("Enter Available Seats: ");
	        int availableSeats = scanner.nextInt();

	        // SQL query to insert flight details
	        String query = "INSERT INTO flights (flightNumber, airline, totalSeats, availableSeats) VALUES (?, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            // Set parameters for the prepared statement
	            preparedStatement.setInt(1, flightNumber);
	            preparedStatement.setString(2, airline);
	            preparedStatement.setInt(3, totalSeats);
	            preparedStatement.setInt(4, availableSeats);

	            // Execute the SQL update statement
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Flight details inserted successfully!");
	            } else {
	                System.out.println("Failed to insert flight details.");
	            }
	            scanner.close();
	           
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    
	}
	
	//	2. displayVacancySeatDetails
	
	public static void displayVacancySeatDetails(Connection connection) {
	    try {
	        // Sample logic to fetch and display information from the flights table
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT * FROM flights");

	        System.out.println("Vacancy Seat Details for All Flights:");
	        System.out.println("------------------------------------");

	        while (resultSet.next()) {
	            System.out.println("Flight Number: " + resultSet.getInt("flightNumber"));
	            System.out.println("Airline: " + resultSet.getString("airline"));
	            System.out.println("Total Seats: " + resultSet.getInt("totalSeats"));
	            System.out.println("Available Seats: " + resultSet.getInt("availableSeats"));
	            System.out.println();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	// 3. updateFlightDetails
	
	public static void updateFlightDetails(Connection connection) {
	    try {
	        connection.setAutoCommit(false); // Start a transaction

	        // Sample logic to get user input for Flight Number to update
	        System.out.print("Enter Flight Number to update: ");
	        int flightNumber = new Scanner(System.in).nextInt();

	        // Sample logic to fetch existing flight details
	        String querySelect = "SELECT * FROM flights WHERE flightNumber = ?";
	        try (PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
	            selectStatement.setInt(1, flightNumber);
	            try (var resultSet = selectStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Display current details
	                    System.out.println("Current Flight Details:");
	                    System.out.println("Flight Number: " + resultSet.getInt("flightNumber"));
	                    System.out.println("Airline: " + resultSet.getString("airline"));
	                    System.out.println("Total Seats: " + resultSet.getInt("totalSeats"));
	                    System.out.println("Available Seats: " + resultSet.getInt("availableSeats"));
	                    System.out.println();
	                } else {
	                    System.out.println("Flight not found with Flight Number: " + flightNumber);
	                    return;
	                }
	            }
	        }

	        // Sample logic to get updated details
	        System.out.print("Enter new Airline: ");
	        String newAirline = new Scanner(System.in).nextLine();

	        System.out.print("Enter new Total Seats: ");
	        int newTotalSeats = new Scanner(System.in).nextInt();

	        // SQL query to update flight details
	        String queryUpdate = "UPDATE flights SET airline = ?, totalSeats = ? WHERE flightNumber = ?";
	        try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
	            updateStatement.setString(1, newAirline);
	            updateStatement.setInt(2, newTotalSeats);
	            updateStatement.setInt(3, flightNumber);

	            // Execute the SQL update statement
	            int rowsAffected = updateStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                connection.commit(); // Commit the transaction
	                System.out.println("Flight details updated successfully!");
	            } else {
	                System.out.println("Failed to update flight details.");
	            }
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
}
