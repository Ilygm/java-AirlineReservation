package MVC.C;

import MVC.M.Flights;

public class FlightController {
    private final Flights flights = new Flights();

    public String[] filterFlights(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return flights.parseFiltered(Flights.makeTempFlight(flightID, origin, destination, date, time, availableSeats, price));
    }
}
