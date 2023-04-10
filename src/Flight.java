public class Flight {
    private final String flightID, origin, destination, date;
    private final short time;
    private short availableSeats;
    private int price;

    public Flight(String flightID, String origin, String destination,String date,short time, short availableSeats) {
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

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
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

    public short getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
