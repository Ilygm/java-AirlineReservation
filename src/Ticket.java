import java.io.Serializable;

public record Ticket(Flight flight, User user, String ticketID)  implements Serializable {
}