package MVC.M;

import java.util.stream.Collectors;

public class Flights extends MapHandler<String, Flight> {

    {
        database.put("FA-17", new Flight("FA-17", "Yazd", "Shiraz", "1401-12-10", 1230, 0, 100_000));
        database.put("TA-55", new Flight("TA-55", "Tehran", "Isfahan", "1401-10-10", 2359, 600, 100_000));
        database.put("GG-55", new Flight("GG-55", "Yazd", "Tehran", "1401-12-10", 2359, 600, 200_000));
        database.put("SS-55", new Flight("SS-55", "Mashhad", "Kerman", "1402-12-10", 2000, 600, 500_000));
        database.put("AA-55", new Flight("AA-55", "Yazd", "Kerman", "1400-05-20", 200, 600, 10_000));
        database.put("AA-46", new Flight("AA-46", "Isfahan", "Tehran", "1400-06-20", 500, 600, 100_000));
        database.put("AA-14", new Flight("AA-14", "Germany", "Iran", "1402-10-20", 900, 0, 100_000));
        database.put("AA-41", new Flight("AA-41", "Iran", "Germany", "1402-10-20", 300, 1, 100_000));
        database.put("FX-45", new Flight("FX-45", "KermanShah", "Ghom", "1402-05-25", 500, 400, 25_000));
        database.put("XF-45", new Flight("XF-45", "Ghom", "KermanShah", "1402-06-25", 500, 1200, 125_000));
    }
    
    public static Flight makeTempFlight(String flightID, String origin, String destination, String date, int time, int availableSeats, int price) {
        return new Flight(flightID, origin, destination, date, time, availableSeats, price);
    }

    public String[] parseFiltered(Flight flight) {
        return filterEntries(flight).stream().map(Flight::toString).collect(Collectors.joining(",,")).split(",,");
    }
}
