package bustrips.model;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BusArrival {
    public String routeName;
    public List<LocalTime> times;

    public BusArrival(String routeName, List<LocalTime> times) {
        this.routeName = routeName;
        this.times = times;
    }

    public String format(boolean relative, LocalTime now) {
        StringBuilder sb = new StringBuilder();
        sb.append(routeName).append(": ");
        for (int i = 0; i < times.size(); i++) {
            LocalTime t = times.get(i);
            if (relative) {
                long min = ChronoUnit.MINUTES.between(now, t);
                sb.append(min).append("min");
            } else {
                sb.append(t);
            }
            if (i != times.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
