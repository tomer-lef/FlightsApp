package db;


public class Flight {

    Long ticket;
    FlightDestination flightDestination;
    String baggage;
    Double price;


    public Flight(long ticket, FlightDestination flightDestination, long price) {
        this.ticket = ticket;
        this.flightDestination = flightDestination;
        this.price = (double) price;
    }


}
