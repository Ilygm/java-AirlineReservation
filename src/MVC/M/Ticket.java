package MVC.M;

public record Ticket(String flightID, String username, String ticketID) {
    public static long TICKET_SIZE = 43;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Ticket ticket) {
            if (!(ticket.flightID() == null || ticket.flightID().equals(flightID))) return false;
            if (!(ticket.username() == null || ticket.username().equals(username))) return false;
            if (!(ticket.ticketID() == null || ticket.ticketID().equals(ticketID))) return false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%36c[ %s --> %s ] ==> %s", ' ',ticketID, username, flightID);
    }

    public String turnToDataLine() {
        return String.format("%5s|%15s|%20s\n", flightID, username, ticketID);
    }
}