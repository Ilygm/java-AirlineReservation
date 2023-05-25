package MVC.M;

public record Ticket(Flight flight, User user, String ticketID) {
    @Override
    public boolean equals(Object o) {
        if (o instanceof Ticket ticket) {
            if (!(ticket.flight() == null || ticket.flight().equals(flight))) return false;
            if (!(ticket.user() == null || ticket.user().equals(user))) return false;
            if (!(ticket.ticketID() == null || ticket.ticketID().equals(ticketID))) return false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%36c%s --> %s\n%36c%s", ' ',ticketID, user.getUsername(), ' ',flight);
    }
}