package bustrips;

import bustrips.logic.BusService;
import bustrips.model.BusArrival;

import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        if (args.length != 3) {
            System.out.println("Wrong input!\nCorrect input: busTrips.Main <stop_id> <max_buses> <relative|absolute>");
            return;
        }

        String stopId = args[0];
        int maxPerLine;
        boolean isRelative;

        try {
            maxPerLine = Integer.parseInt(args[1]);
            if (maxPerLine <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Wrong input!\n<max_buses> must be a positive integer.");
            return;
        }

        if (args[2].equalsIgnoreCase("relative")) {
            isRelative = true;
        } else if (args[2].equalsIgnoreCase("absolute")) {
            isRelative = false;
        } else {
            System.out.println("Wrong input!\n<relative|absolute> must be either 'relative' or 'absolute'.");
            return;
        }

        // for fixed time of 12:00
        // LocalTime now = LocalTime.of(12, 0);
        LocalTime now = LocalTime.now(); // Use actual system time

        BusService service = new BusService("data/");
        if (!service.isValidStop(stopId)) {
            System.out.println("Wrong input!\n<stop_id> of:' "+stopId+" 'does not exist.\n\nHere are all the possible stops:");
            service.printAllStops();
            return;
        }

        List<BusArrival> arrivals = service.getUpcomingArrivals(stopId, maxPerLine, now);
        System.out.println("Postajalisce " + service.getStopName(stopId));
        for (BusArrival arrival : arrivals) {
            System.out.println(arrival.format(isRelative, now));
        }
    }
}