package MVC.M;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Tickets extends FileWriter{
    public Tickets() {
        super(Ticket.TICKET_SIZE, "Tickets.dat");
    }

    public List<Ticket> usersInFlight(Flight flight) throws IOException {
        return filterEntries(new Ticket(flight.getFlightID(), null, null));
    }

    public List<Ticket> flightsForUser(User user) throws IOException {
        return filterEntries(new Ticket(null, user.getUsername(), null));
    }

    public String[] parseTicketList(List<Ticket> list) {
        return list.stream().map(Ticket::toString).collect(Collectors.joining(",,")).split(",,");
    }

    public boolean addTicket(User user, Flight flight) {
        if (user != null && flight != null && flight.getAvailableSeats() > 0) {
            Ticket tempTicket = new Ticket(flight.getFlightID(), user.getUsername(), flight.getAvailableSeats() + "_" + user.getUsername());
            appendRecord(tempTicket.turnToDataLine());
            return true;
        } else return false;
    }

    public void removeTicket(String ticketID) {
        removeEntry(ticketID);
    }

    public void removeFlight(String flightID) throws IOException {
        filterEntries(new Ticket(flightID, null, null)).forEach(v -> removeTicket(v.ticketID()));
    }

    public List<Ticket> filterEntries(Ticket ticket) throws IOException {
        List<Ticket> tickets = new LinkedList<>();
        readAllRecords().forEach(t -> tickets.add(dataLineToTicket(t)));
        return tickets.stream().filter(t -> t.equals(ticket)).toList();
    }

    public Ticket dataLineToTicket(String data) {
        String[] splitData = data.split("\\|");
        return new Ticket(splitData[0].strip(), splitData[1].strip(), splitData[2].strip());
    }

    public Ticket findTicket(String ticketID) throws IOException {
        return dataLineToTicket(readRecord(findFieldSpecific(22, 20, ticketID)));
    }
}
