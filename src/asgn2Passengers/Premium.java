/**
 * 
 */
package asgn2Passengers;

/**
 * @author Matt Baker
 *
 */
public class Premium extends Passenger {

	/**
	 * Premium Constructor (Partially Supplied)
	 * Passenger is created in New state, later given a Confirmed Premium Class reservation, 
	 * Queued, or Refused booking if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight.  
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @see asgnPassengers.Passenger#Passenger(int,int)
	 */
	public Premium(int bookingTime,int departureTime) throws PassengerException {
		//TODO: (Declan)
		//See Economy constructor.
		super(bookingTime, departureTime);
		
		this.passID = "P:" + this.passID;
	}
	
	/**
	 * Simple constructor to support {@link asgn2Passengers.Passenger#upgrade()} in other subclasses
	 */
	protected Premium() {
		
	}

	@Override
	public Passenger upgrade() {
		
		Business upgradePassenger = new Business();
		copyPassengerState(upgradePassenger);
		
		return upgradePassenger;
		
	}
	
	@Override
	public String noSeatsMsg() {
		return "No seats available in Premium";
	}
}
