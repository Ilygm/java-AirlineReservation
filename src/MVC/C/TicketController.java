package MVC.C;

import MVC.M.Tickets;
import MVC.M.Users;

public class TicketController {

    protected final Tickets tickets = new Tickets();

    public String[] getUsersTickets(String username) {
        return tickets.parseTicketList(tickets.flightsForUser(Users.makeTempUser(username, null)));
    }
}
