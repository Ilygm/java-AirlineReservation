package MVC.C;

import MVC.M.Tickets;
import MVC.M.Users;

import java.util.List;

public class TicketController {
    private final Tickets tickets = new Tickets();

    public List<?> getUsersTickets(String username) {
        return tickets.flightsForUser(Users.makeTempUser(username, null));
    }

    public List<?> getFlightsPassengers(String flightID) {
        return null; // TODO: 5/24/2023
    }
}
