package db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import java.util.*;

@Configuration
@ComponentScan
public class FlightsManager {

    public static final int FLIGHTS_LIMIT = 50;
    Map<Long, Flight> myFlights= new HashMap();
    Map<Long, FlightDestination> allFlightDest= new HashMap();
    Map<Integer, Integer> couponsId = new HashMap();


    @Bean
    void init(){
        for (long i = 1; i<= FLIGHTS_LIMIT; i++){
            FlightDestination destination = new FlightDestination(i*2);
            allFlightDest.put(destination.getDestinationId(), destination);
            Flight flight = new Flight(i, destination, i*7 );
            myFlights.put(i, flight);
        }
        createCoupons();
    }

    private void createCoupons() {
        Random rd = new Random();
        Integer[] intArrayDiscount = {10, 50, 60};

        for (int i = 0 ; i < 7; i++) {
            if (i>=intArrayDiscount.length){
                shuffleDiscount(intArrayDiscount);
                couponsId.put(rd.nextInt(), intArrayDiscount[0]);
            }
            couponsId.put(rd.nextInt(), intArrayDiscount[i]);
        }
    }

    private void shuffleDiscount(Integer[] intArrayDiscount) {
        List<Integer> intList = Arrays.asList(intArrayDiscount);
        Collections.shuffle(intList);
        intList.toArray(intArrayDiscount);
    }


    public boolean findTicket(long ticketId) {
        return myFlights.containsKey(ticketId);
    }

    public boolean checkIn(Long destinationId, String baggage) {
        return allFlightDest.containsKey(destinationId);
    }


    public Map<Boolean, Double> ticketPrice(long couponId, double price) {
        HashMap<Boolean, Double> map = new HashMap<>();
        map.put(false, price);
        if (couponsId.containsKey(couponId)){
            price = calculatePrice(couponId, price);
            map.put(true, price);
        }
        return map;
    }

    private double calculatePrice(long couponId, double price) {
        int discountPercent = couponsId.get(couponId);
        int payPercent = 1 - (discountPercent / 100);
        return price * payPercent;
    }
}
