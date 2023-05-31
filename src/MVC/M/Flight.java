package MVC.M;

public class Flight {
    public static long FLIGHT_SIZE = 90;
    public static long FLIGHT_ID_SIZE = 5;
    private final String flightID;
    private String origin;
    private String destination;
    private String date;
    private int availableSeats;
    private int time;
    private int price;

    public Flight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        this.flightID = flightID;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.availableSeats = (short) availableSeats;
        this.price = price;
    }

    public String flightToDataLine() {
        return String.format("%5s|%25s|%25s|%10s|%5s|%4s|%10s", flightID, origin, destination, date, availableSeats, time, price);
    }

    public String getFlightID() {
        return flightID;
    }

    public String getOrigin() {
        return origin;
    }

    public Flight setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Flight setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Flight setDate(String date) {
        this.date = date;
        return this;
    }

    public int getTime() {
        return time;
    }

    public Flight setTime(int time) {
        this.time = time;
        return this;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public Flight setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
        return this;
    }

    public Flight changeAvailableSeats(int byAmount) {
        availableSeats += byAmount;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Flight setPrice(int price) {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Flight flight) {
            if (!(flight.getFlightID() == null) && flight.getFlightID().equalsIgnoreCase(flightID)) return true;
            if (!(flight.getOrigin() == null || flight.getOrigin().equalsIgnoreCase(origin))) return false;
            if (!(flight.getDestination() == null || flight.getDestination().equalsIgnoreCase(destination)))
                return false;
            if (!(flight.getDate() == null || flight.getDate().equalsIgnoreCase(date))) return false;
            if (!(flight.getAvailableSeats() == -1 || flight.getAvailableSeats() == availableSeats)) return false;
            if (!(flight.getTime() == -1 || flight.getTime() == time)) return false;
            if (!(flight.getPrice() == -1 || flight.getPrice() >= price)) return false;

            return true;
        } else return false;
    }

    /**
     * Turn integer time to string and makes sure the zeros stay
     *
     * @param time time given in ____ integer form
     * @return Formatted string as __:__
     */
    private String turnTimeToString(int time) {
        if (time == 0) return "00:00";

        int leftSide = time / 100;
        int rightSide = time % 100;

        if (leftSide == 0) {
            if (rightSide > 9) return "00:" + time;
            else return "00:0" + time;
        } else {
            if (leftSide > 9) { //10:xx
                if (rightSide > 9) return leftSide + ":" + rightSide;
                else return leftSide + ":0" + rightSide;
            } else { //09:00
                if (rightSide > 9) return "0" + leftSide + ':' + rightSide;
                else return "0" + leftSide + ":0" + rightSide;
            }
        }
    }

    @Override
    public String toString() {
        return String.format(" %-4s | %-15s | %-15s | %-10s | %,-10d  | %-5s  | %-,5d\n", flightID, origin, destination, date, price, turnTimeToString(time), availableSeats);
    }
}
