public class Flight {
    private String flightID, origin, destination, date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void changeAvailableSeats(int byAmount) {
        availableSeats += byAmount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Turn integer time to string and makes sure the zeros stay
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
        return String.format("%31c %-4s | %-15s | %-15s | %-10s | %,-10d  | %-5s  | %-,5d\n", ' ', flightID, origin, destination, date, price, turnTimeToString(time), availableSeats);
    }
}
