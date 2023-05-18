package MVC.M;

import java.util.List;

public class Tickets extends MapHandler<String, Ticket> {
    public List<Ticket> usersInFlight(Flight flight) {
        return filterEntries(new Ticket(flight, null ,null));
    }

    public List<Ticket> flightsForUser(User user) {
        return filterEntries(new Ticket(null, user ,null));
    }
}
