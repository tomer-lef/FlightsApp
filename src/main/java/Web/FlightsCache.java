package Web;

import db.FlightCache;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.HashMap;

import java.util.LinkedList;

@Component
public class FlightsCache {

    private final int CACHE_SIZE;

    private Deque<FlightCache> flightQueue;
    private HashMap<Long, Long> hashMap;
    private long maxTimeToLive;


    FlightsCache(int capacity) {
        flightQueue = new LinkedList<>();
        hashMap = new HashMap<>();
        CACHE_SIZE = capacity;
        maxTimeToLive = 12 * 60 * 60 * 1000; // 12 hours in milliseconds
    }

    public boolean get(long ticketId) {
        Long timeExpire = 0L;
        if (!hashMap.containsKey(ticketId)) {
            if (flightQueue.size() == CACHE_SIZE) {
                FlightCache lastFlight = flightQueue.removeLast();
                hashMap.remove(lastFlight.getTicket());
            }
        }
        else {
            flightQueue.remove(ticketId);
            timeExpire = hashMap.get(ticketId);
        }
         if (System.currentTimeMillis() - timeExpire > maxTimeToLive) {
             return false;
         }
        FlightCache flightCache = new FlightCache(ticketId);
        flightQueue.push(flightCache);
        hashMap.put(ticketId, System.currentTimeMillis());
        return true;
    }


    public void put(long ticketId) {
        if (flightQueue.contains(ticketId))
            flightQueue.remove(ticketId);
        else if (flightQueue.size() == CACHE_SIZE) {
            FlightCache firstFlightCache = flightQueue.iterator().next();
            flightQueue.remove(firstFlightCache);
        }
        FlightCache FlightCache = new FlightCache(ticketId);
        flightQueue.add(FlightCache);
        hashMap.put(ticketId, System.currentTimeMillis());
    }

}
