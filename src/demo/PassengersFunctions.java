package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PassengersFunctions {
	public static void insertPassengerDetails(Connection connection) {
	    try {
	        // Get passenger details from user input or any other source
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("Enter Passenger ID: ");
	        int passengerId = scanner.nextInt();

	        scanner.nextLine(); // Consume the newline character

	        System.out.print("Enter Passenger Name: ");
	        String passengerName = scanner.nextLine();

	        System.out.print("Enter Passenger Age: ");
	        int passengerAge = scanner.nextInt();

	        // SQL query to insert passenger details
	        String query = "INSERT INTO passengers (id, name, age) VALUES (?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            // Set parameters for the prepared statement
	            preparedStatement.setInt(1, passengerId);
	            preparedStatement.setString(2, passengerName);
	            preparedStatement.setInt(3, passengerAge);

	            // Execute the SQL update statement
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Passenger details inserted successfully!");
	            } else {
	                System.out.println("Failed to insert passenger details.");
	            }
	            scanner.close();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// 2. updatePassesngerInformation
	
	public static void updatePassengerInformation(Connection connection) {
	    try {
	        connection.setAutoCommit(false); // Start a transaction

	        // Sample logic to get user input for Passenger ID to update
	        System.out.print("Enter Passenger ID to update: ");
	        int passengerId = new Scanner(System.in).nextInt();

	        // Sample logic to fetch existing passenger details
	        String querySelect = "SELECT * FROM passengers WHERE id = ?";
	        try (PreparedStatement selectStatement = connection.prepareStatement(querySelect)) {
	            selectStatement.setInt(1, passengerId);
	            try (var resultSet = selectStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Display current details
	                    System.out.println("Current Passenger Details:");
	                    System.out.println("ID: " + resultSet.getInt("id"));
	                    System.out.println("Name: " + resultSet.getString("name"));
	                    System.out.println("Age: " + resultSet.getInt("age"));
	                    System.out.println();
	                } else {
	                    System.out.println("Passenger not found with ID: " + passengerId);
	                    return;
	                }
	            }
	        }

	        // Sample logic to get updated details
	        System.out.print("Enter new Passenger Name: ");
	        String newName = new Scanner(System.in).nextLine();

	        System.out.print("Enter new Passenger Age: ");
	        int newAge = new Scanner(System.in).nextInt();

	        // SQL query to update passenger details
	        String queryUpdate = "UPDATE passengers SET name = ?, age = ? WHERE id = ?";
	        try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
	            updateStatement.setString(1, newName);
	            updateStatement.setInt(2, newAge);
	            updateStatement.setInt(3, passengerId);

	            // Execute the SQL update statement
	            int rowsAffected = updateStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                connection.commit(); // Commit the transaction
	                System.out.println("Passenger information updated successfully!");
	            } else {
	                System.out.println("Failed to update passenger information.");
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
