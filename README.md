# Airline Ticket Booking Application

## Overview

The Airline Ticket Booking Application is a Java application that allows users to book and manage airline tickets. It provides functionalities such as passenger details entry, flight details entry, ticket booking, ticket cancellation, display of vacancy seat details, and updates to passenger and flight information.

## Features

1. **Insert Passenger Details:**
    - Collects and stores passenger information, including name, ID, and age.
  
2. **Insert Flight Details:**
    - Manages flight details, including flight number, airline, total seats, and available seats.

3. **Book Ticket:**
    - Allows users to book tickets by selecting a passenger and a flight.
    - Updates the available seats in the flights table and records the booking in the bookings table.

4. **Cancel Ticket:**
    - Enables users to cancel booked tickets.
    - Updates the available seats in the flights table and removes the booking record from the bookings table.

5. **Display Vacancy Seat Details:**
    - Shows the vacancy seat details for all flights.
  
6. **Update Passenger Information:**
    - Allows users to update passenger information based on the passenger ID.

7. **Update Flight Details:**
    - Permits users to update flight details based on the flight number.

## Usage

1. **Compile and Run:**
    - Compile the Java code and run the application.
    ```bash
    javac AirlineTicketBookingApp.java
    java AirlineTicketBookingApp
    ```

2. **Menu Driven Interface:**
    - Use the menu-driven interface to navigate through different functionalities.

3. **Follow Instructions:**
    - Follow the instructions prompted by the application to perform specific operations.

## Database Configuration

- Ensure that MySQL is installed and running.
- Update the database connection details in the `AirlineTicketBookingApp` class.

```java
private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

## Dependencies

- The application uses JDBC for database connectivity.
- Make sure to have the MySQL JDBC driver in your classpath.

## Additional Notes

- Customize the application based on your database schema and specific requirements.
- Ensure proper error handling and exception management in a production environment.

## Contributors

- Kunal Yadav

## License

This project is licensed under the [MIT License](LICENSE).
