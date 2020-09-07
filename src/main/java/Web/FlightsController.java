package Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FlightsController {

    @Autowired
    FlightsService flightsService;

    @PostMapping("/getTicket")
    boolean getTicket(@NonNull long ticket_id) {
        return flightsService.findTicket(ticket_id);
    }

    @PostMapping("/checkIn")
    boolean checkIn(@NonNull long destination, @NonNull String baggage){
        return flightsService.checkIn(destination,baggage);
    }

    @PostMapping("/ticketPrice")
    Map<Boolean, Double> ticketPrice(long couponId, double price){
        return flightsService.ticketPrice(couponId, price);
    }

}
