/**
 * 
 */
package asgn2Passengers;

/**
 * @author Matt Baker
 *
 */
public class Economy extends Passenger {

	/**
	 * Economy Constructor (Partially Supplied)
	 * Passenger is created in New state, later given a Confirmed Economy Class reservation, 
	 * Queued, or Refused booking if waiting list is full. 
	 * 
	 * @param bookingTime <code>int</code> day of the original booking. 
	 * @param departureTime <code>int</code> day of the intended flight.  
	 * @throws PassengerException if invalid bookingTime or departureTime 
	 * @see asgnPassengers.Passenger#Passenger(int,int)
	 */
	public Economy(int bookingTime,int departureTime) throws PassengerException {
		//TODO: (Declan)
		//Looks like when the constructor is called without parameters the
		//newState is never set and the simulation will always throw an exception.
		//For now, I have changed this to pass parameters.
		super(bookingTime, departureTime);
		
		this.newState = true;		
		this.passID = "Y:" + this.passID;
	}
	
	@Override
	public String noSeatsMsg() {
		return "No seats available in Economy";
	}

	@Override
	public Passenger upgrade() {
		
		Premium upgradePassenger = new Premium();
		copyPassengerState(upgradePassenger);
		
		return upgradePassenger;
		
	}
}
