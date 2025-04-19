package bustrips.data;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

public class GtfsReader {
    private final String dir;
    private final Map<String, String> tripToRoute = new HashMap<>();
    private final Map<String, String> routeNames = new HashMap<>();
    private final Map<String, String> stopNames = new HashMap<>();

    public GtfsReader(String dir) throws IOException {
        this.dir = dir;
        loadTrips();
        loadRoutes();
        loadStops();
    }

    public void loadTrips() throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(dir + "/trips.txt"))) {
            String line = r.readLine().replace("\uFEFF", "");
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                tripToRoute.put(parts[2], parts[0]);
            }
        }
    }

    public void loadRoutes() throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(dir + "/routes.txt"))) {
            String line = r.readLine().replace("\uFEFF", "");
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                routeNames.put(parts[0], parts[2]);
            }
        }
    }

    public void loadStops() throws IOException {
        try (BufferedReader r = new BufferedReader(new FileReader(dir + "/stops.txt"))) {
            String line = r.readLine().replace("\uFEFF", "");
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                stopNames.put(parts[0], parts[2]);
            }
        }
    }

    public Map<String, LocalTime> streamStopTimesForStop(String stopId, LocalTime until) {
        Map<String, LocalTime> result = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(dir + "/stop_times.txt"))) {
            String line = r.readLine().replace("\uFEFF", "");
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[3].equals(stopId)) continue;
                LocalTime time = LocalTime.parse(parts[1]);
                if (time.isAfter(until)) continue;
                result.put(parts[0], time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getRouteIdForTrip(String tripId) {
        return tripToRoute.get(tripId);
    }

    public String getRouteName(String routeId) {
        return routeNames.get(routeId);
    }

    public String getStopName(String stopId) {
        return stopNames.getOrDefault(stopId, "StopId not found");
    }

    public boolean hasStop(String stopId) {
        return stopNames.containsKey(stopId);
    }

    public void printAllStops() {
        stopNames.entrySet().stream()
            .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getKey())))
            .forEach(entry ->
                    System.out.println(entry.getKey() + " -> " + entry.getValue())
            );
    }
}
