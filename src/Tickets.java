import java.util.ArrayList;

public class Tickets {
    public ArrayList<Ticket> ticketsDB = new ArrayList<>();

/*
    THIS FUNCTION IS WORKING BUT IT'S NOT USED CURRENTLY
    public ArrayList<Flight> flightsForUser(User user) {
        ArrayList<Flight> ownedFlights = new ArrayList<>();
        for (Ticket ticket : ticketsDB.values()) {
            if (ticket.user().equals(user)) ownedFlights.add(ticket.flight());
        }
        return ownedFlights;
    }
*/

    /**
     * Used to find passengers in a flight
     * @param flight flight's information
     * @return List of the passengers
     */
    public ArrayList<User> usersForFlight(Flight flight) {
        ArrayList<User> ownedFlights = new ArrayList<>();
        for (Ticket ticket : ticketsDB) {
            if (ticket.flight().equals(flight)) ownedFlights.add(ticket.user());
        }
        return ownedFlights;
    }

    /**
     * Prints the users bought tickets
     * @param user used to find their tickets
     */
    public void printOwnedTickets(User user) {
        for (Ticket ticket : ticketsDB) {
            if (ticket.user().equals(user)) {
                System.out.printf("""
                        %31cTicketID: %-10s  | UserName: %-10s
                                                
                        %31c   ID  |      Origin     |   Destination   |    Date    |    Price    |  Time  | Seats
                        %s
                        %31c%s
                        """, ' ', ticket.ticketID(), user.getUsername(), ' ', ticket.flight().toString(), ' ', "_".repeat(86));
            }
        }
    }

    /**
     * Prints passengers in a flight
     * @param flight flight's information
     */
    public void printPassengerList(Flight flight) {
        System.out.printf("%50cTicketID   --> UserName  \n", ' ');
        for (Ticket ticket : ticketsDB) {
            if (ticket.flight().equals(flight)) {
                System.out.printf("%50c%-10s --> %-10s\n", ' ', ticket.ticketID(), ticket.user().getUsername());
            }
        }
    }

    /**
     * Removes a ticket from database
     * @param flight used to find the tickets
     */
    public void removeTicket(Flight flight){
        ticketsDB.removeIf(ticket -> ticket.flight().equals(flight));
    }
}