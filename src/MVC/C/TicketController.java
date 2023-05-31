package MVC.C;

import MVC.M.Tickets;
import MVC.M.Users;

import java.io.IOException;

public class TicketController {

    protected final Tickets tickets = new Tickets();

    public String[] getUsersTickets(String username) {
        try {
            return tickets.parseTicketList(tickets.flightsForUser(Users.makeTempUser(username, null)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
