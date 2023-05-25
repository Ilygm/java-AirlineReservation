package MVC.M;

import java.util.List;
import java.util.stream.Collectors;

public class Tickets extends MapHandler<String, Ticket> {
    public List<Ticket> usersInFlight(Flight flight) {
        return filterEntries(new Ticket(flight, null, null));
    }

    public List<Ticket> flightsForUser(User user) {
        return filterEntries(new Ticket(null, user, null));
    }

    public String[] parseTicketList(List<Ticket> list) {
        return list.stream().map(Ticket::toString).collect(Collectors.joining(",,")).split(",,");
    }

    public boolean addTicket(User user, Flight flight) {
        if (user != null && flight != null && flight.getAvailableSeats() > 0) {
            Ticket tempTicket = new Ticket(flight, user, flight.getAvailableSeats() + "_" + user.getUsername());
            return addEntry(tempTicket.ticketID(), tempTicket);
        } else return false;
    }

    public boolean removeTicket(String ticketID) {
        return removeEntry(ticketID);
    }
}
