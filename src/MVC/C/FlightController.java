package MVC.C;

import MVC.M.Flights;

public class FlightController {

    protected final Flights flights = new Flights();

    public String[] filterFlights(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return flights.parseFiltered(Flights.makeTempFlight(flightID, origin, destination, date, time, availableSeats, price));
    }

    public boolean doesFlightExist(String flightID) {
        return flights.entryExists(flightID);
    }

    public boolean reduceOneSeats(String flightID) {
        return flights.reduceOneSeat(flightID);
    }

    public int getFlightPrice(String flightID) {
        return flights.returnFlightPrice(flightID);
    }

    public String getFlight(String flightID) {
        return flights.findEntry(flightID).toString();
    }

    public boolean registerNewFlight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return flights.addFlight(flightID, origin, destination, date, time, availableSeats, price);
    }

    public void changeOrigin(String flightID, String origin) {
        flights.findEntry(flightID).setOrigin(origin);
    }

    public void changeDestination(String flightID, String destination) {
        flights.findEntry(flightID).setDestination(destination);
    }

    public void changeDate(String flightID, String date) {
        flights.findEntry(flightID).setDate(date);
    }

    public void changeTime(String flightID, int time) {
        flights.findEntry(flightID).setTime(time);
    }

    public void changeSeats(String flightID, int seats) {
        flights.findEntry(flightID).setAvailableSeats(seats);
    }

    public void changePrice(String flightID, int price) {
        flights.findEntry(flightID).setPrice(price);
    }

}
