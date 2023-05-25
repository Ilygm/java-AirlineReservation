package MVC.C;

import MVC.M.Flights;

public class FlightController{

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
}
