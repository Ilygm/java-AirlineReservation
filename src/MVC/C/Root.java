package MVC.C;

import MVC.M.Flights;
import MVC.M.Tickets;
import MVC.M.Users;

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
        return tickets.addTicket(Users.makeTempUser(username, null), flights.findEntry(flightID));
    }

    public void cancelTicket(String ticketID) {
        users.addUserBalance(tickets.findEntry(ticketID).user(), tickets.findEntry(ticketID).flight().getPrice());
        flights.findEntry(tickets.findEntry(ticketID).flight().getFlightID()).changeAvailableSeats(1);
        tickets.removeTicket(ticketID);
    }

    public void removeFlight(String flightID) {
        tickets.usersInFlight(Flights.makeTempFlight(flightID)).forEach(v -> users.addUserBalance(v.user() ,v.flight().getPrice()));
        tickets.removeFlight(flightID);
        flights.removeEntry(flightID);
    }
}
