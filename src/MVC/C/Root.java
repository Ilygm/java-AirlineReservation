package MVC.C;

import MVC.M.Flights;
import MVC.M.Tickets;
import MVC.M.Users;

import java.io.IOException;

public class Root {

    private final Flights flights;
    private final Users users;
    private final Tickets tickets;

    public Root(FlightController flightController, UserController userController, TicketController ticketController) {
        this.flights = flightController.flights;
        this.users = userController.users;
        this.tickets = ticketController.tickets;
    }

    public boolean reserveFlight(String username, String flightID) {
        try {
            return tickets.addTicket(Users.makeTempUser(username, null), flights.getFlight(flightID));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelTicket(String ticketID) {
        try {
            users.addUserBalance(tickets.findTicket(ticketID).username(), flights.returnFlightPrice(tickets.findTicket(ticketID).flightID()));
            flights.overrideFlight(flights.getFlight(tickets.findTicket(ticketID).flightID()).changeAvailableSeats(1));
            tickets.removeTicket(ticketID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFlight(String flightID) {
        try {
            tickets.usersInFlight(Flights.makeTempFlight(flightID)).forEach(v -> {
                try {
                    users.addUserBalance(v.username(), flights.returnFlightPrice(v.flightID()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            tickets.removeFlight(flightID);
            flights.removeFlight(flightID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
