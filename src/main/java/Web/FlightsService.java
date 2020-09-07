package Web;

import db.FlightCache;
import db.FlightsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FlightsService {

    FlightsManager flightsManager;
    FlightsCache cache;

    @Autowired
    public FlightsService(FlightsManager flightsManager, FlightsCache cache) {
        this.flightsManager = flightsManager;
        this.cache = cache;
    }

    public boolean findTicket(long ticketId) {
        boolean result = cache.get(ticketId);
        if (!result){
            result = flightsManager.findTicket(ticketId);
            if (result) {
                cache.put(ticketId);
            }
        }
        return result;
    }

    public boolean checkIn(Long destination, String baggage) {
        return flightsManager.checkIn(destination, baggage);
    }

    public Map<Boolean, Double> ticketPrice(long couponId, double price) {
        return flightsManager.ticketPrice(couponId, price);

    }
}
