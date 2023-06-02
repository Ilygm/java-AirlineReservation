package MVC.M;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Flights extends FileWriter {

    {
        appendRecord(new Flight("FA-17", "Yazd", "Shiraz", "1401-12-10", 1230, 0, 100_000).flightToDataLine());
        appendRecord(new Flight("TA-55", "Tehran", "Isfahan", "1401-10-10", 2359, 600, 100_000).flightToDataLine());
        appendRecord(new Flight("GG-55", "Yazd", "Tehran", "1401-12-10", 2359, 600, 200_000).flightToDataLine());
        appendRecord(new Flight("SS-55", "Mashhad", "Kerman", "1402-12-10", 2000, 600, 500_000).flightToDataLine());
        appendRecord(new Flight("AA-55", "Yazd", "Kerman", "1400-05-20", 200, 600, 10_000).flightToDataLine());
        appendRecord(new Flight("AA-46", "Isfahan", "Tehran", "1400-06-20", 500, 600, 100_000).flightToDataLine());
        appendRecord(new Flight("AA-14", "Germany", "Iran", "1402-10-20", 900, 0, 100_000).flightToDataLine());
        appendRecord(new Flight("AA-41", "Iran", "Germany", "1402-10-20", 300, 1, 100_000).flightToDataLine());
        appendRecord(new Flight("FX-45", "KermanShah", "Ghom", "1402-05-25", 500, 400, 25_000).flightToDataLine());
        appendRecord(new Flight("XF-45", "Ghom", "KermanShah", "1402-06-25", 500, 1200, 125_000).flightToDataLine());
    }

    public Flights() {
        super(Flight.FLIGHT_SIZE, ".\\Data\\Flights.dat");
    }

    public static Flight makeTempFlight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return new Flight(flightID, origin, destination, date, time, availableSeats, price);
    }

    public static Flight makeTempFlight(String flightID) {
        return new Flight(flightID, null, null, null, -1, -1, -1);
    }

    public String[] parseFiltered(Flight flight) throws IOException {
        List<Flight> allFlights = new LinkedList<>();
        for (String readAllRecord : readAllRecords()) {
            allFlights.add(dataLineToFlight(readAllRecord));
        }
        return allFlights.stream().filter(f -> f.equals(flight)).map(Flight::toString).collect(Collectors.joining(",,")).split(",,");
    }

    public int returnFlightPrice(String flightID) throws IOException {
        return getFlight(flightID).getPrice();
    }

    public boolean addFlight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return appendRecord(new Flight(flightID, origin, destination, date, time, availableSeats, price).flightToDataLine());
    }

    public void overrideFlight(Flight modifiedFlight) throws IOException {
        overrideRecord(modifiedFlight.flightToDataLine(), findFieldSpecific(0, 5, modifiedFlight.getFlightID()));
    }

    public Flight dataLineToFlight(String dataLine) {
        String[] data = dataLine.split("\\|");
        return makeTempFlight(data[0], data[1].strip(), data[2].strip(), data[3], Integer.parseInt(data[4].strip()), Integer.parseInt(data[5].strip()), Integer.parseInt(data[6].strip()));
    }

    public Flight getFlight(String flightID) throws IOException {
        int idx = findFieldSpecific(0, 5, flightID);
        if (idx != -1) {
            String dataLine = readRecord(findFieldSpecific(0, 5, flightID));
            return dataLineToFlight(dataLine);
        } else
            return null;
    }

    public boolean doesFlightExist(String flightID) throws IOException {
        int status = findFieldSpecific(0, 5, flightID);
        return status != -1;
    }

    public void removeFlight(String flightID) throws IOException {
        removeDataLine(findFieldSpecific(0, 5, flightID));
    }
}
