public class Flight {
    private String flightID, origin, destination, date;
    private short availableSeats;
    private int price;
    private short time;

    public Flight(String flightID, String origin, String destination, String date, short time, short availableSeats) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(short availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public short getTime() {
        return time;
    }

    public void setTime(short time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(" %-4s | %-10s | %-10s | %-10s | %-10s | %-10d_", flightID, origin, destination, date, price);
    }
}
