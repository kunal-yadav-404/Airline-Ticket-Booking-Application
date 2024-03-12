package demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import java.util.Scanner;

public class AirlineTicketBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        try (Connection connection = DatabaseUtil.getConnection()) {
            do {
                System.out.println("Airline Ticket Booking System");
                System.out.println("1. Insert Passenger Details");
                System.out.println("2. Insert Flight Details");
                System.out.println("3. Book Ticket");
                System.out.println("4. Cancel Ticket");
                System.out.println("5. Display Vacancy Seat Details");
                System.out.println("6. Update Passenger Information");
                System.out.println("7. Update Flight Details");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                

                
                choice = scanner.nextLine().charAt(0);
                
                switch (choice) {
                    case '1':
                        PassengersFunctions.insertPassengerDetails(connection);
                        break;
                    case '2':
                        FlightFunctions.insertFlightDetails(connection);
                        break;
                    case '3':
                        BookingFunctions.bookTicket(connection);
                        break;
                    case '4':
                        BookingFunctions.cancelTicket(connection);
                        break;
                    case '5':
                        FlightFunctions.displayVacancySeatDetails(connection);
                        break;
                    case '6':
                        PassengersFunctions.updatePassengerInformation(connection);
                        break;
                    case '7':
                        FlightFunctions.updateFlightDetails(connection);
                        break;
                    case '8':
                        System.out.println("Exiting the application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } while (choice != '8');
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
}
