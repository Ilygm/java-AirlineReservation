public class Flight {
    private String flightID, origin, destination, date, time;
    private short availableSeats;

    private int price;

    public Flight(String flightID, String origin, String destination, String date, String time, int availableSeats, int price) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.availableSeats = (short) availableSeats;
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = (short) availableSeats;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format("%31c %-4s | %-15s | %-15s | %-10s | %,-10d |  %5s | %,5d\n", ' ', flightID, origin, destination, date, price, time, availableSeats);
    }
}
