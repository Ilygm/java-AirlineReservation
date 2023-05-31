package MVC.C;

import MVC.M.Flights;

import java.io.IOException;

public class FlightController {

    protected final Flights flights = new Flights();

    public String[] filterFlights(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        try {
            return flights.parseFiltered(Flights.makeTempFlight(flightID, origin, destination, date, time, availableSeats, price));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doesFlightExist(String flightID) {
        try {
            return flights.doesFlightExist(flightID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean reduceOneSeats(String flightID) {
        try {
            int availableSeats = flights.getFlight(flightID).getAvailableSeats();
            if (availableSeats != 0) {
                flights.overrideFlight(flights.getFlight(flightID).changeAvailableSeats(-1));
                return true;
            } else return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getFlightPrice(String flightID) {
        try {
            return flights.returnFlightPrice(flightID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFlight(String flightID) {
        try {
            return flights.getFlight(flightID).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registerNewFlight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return flights.addFlight(flightID, origin, destination, date, time, availableSeats, price);
    }

    public void changeOrigin(String flightID, String origin) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setOrigin(origin));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeDestination(String flightID, String destination) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setDestination(destination));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeDate(String flightID, String date) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setDate(date));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeTime(String flightID, int time) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setTime(time));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeSeats(String flightID, int seats) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setAvailableSeats(seats));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePrice(String flightID, int price) {
        try {
            flights.overrideFlight(flights.getFlight(flightID).setPrice(price));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
