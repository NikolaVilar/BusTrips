package bustrips.logic;

import bustrips.data.GtfsReader;
import bustrips.model.BusArrival;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class BusService {
    private final GtfsReader reader;

    public BusService(String dataDir) throws IOException {
        this.reader = new GtfsReader(dataDir);
    }

    public List<BusArrival> getUpcomingArrivals(String stopId, int maxPerLine, LocalTime now) throws IOException {
        Map<String, List<LocalTime>> routeToTimes = new HashMap<>();

        reader.streamStopTimesForStop(stopId, now.plusHours(2)).forEach((tripId, arrivalTime) -> {
            String routeId = reader.getRouteIdForTrip(tripId);
            String routeName = reader.getRouteName(routeId);
            routeToTimes.putIfAbsent(routeName, new ArrayList<>());
            routeToTimes.get(routeName).add(arrivalTime);
        });

        List<BusArrival> result = new ArrayList<>();
        for (var entry : routeToTimes.entrySet()) {
            List<LocalTime> sorted = entry.getValue().stream()
                .filter(t -> !t.isBefore(now) && !t.isAfter(now.plusHours(2)))
                .sorted()
                .limit(maxPerLine)
                .collect(Collectors.toList());
            if (!sorted.isEmpty()) result.add(new BusArrival(entry.getKey(), sorted));
        }
        return result;
    }

    public String getStopName(String stopId) throws IOException {
        return reader.getStopName(stopId);
    }

    public boolean isValidStop(String stopId) {
        return reader.hasStop(stopId);
    }

    public void printAllStops() {
        reader.printAllStops();
    }
}
