public class Flight {
    private String flightID, origin, destination, date;
    private short availableSeats;
    private int price;
    private short time;

    public Flight(String flightID, String origin, String destination, String date,int time,int availableSeats, int price) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = (short)time;
        this.availableSeats = (short) availableSeats;
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
        this.availableSeats = (short )availableSeats;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(int time) {
        this.time = (short)time;
    }

    @Override
    public String toString() {
        return String.format(" %-4s | %-10s | %-10s | %-10s | %-10d_ | %4d | %5d_", flightID, origin, destination, date, price, time, availableSeats);
    }
}
