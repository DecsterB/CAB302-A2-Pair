package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.First;

public class FirstTests
{
	final int BOOKING_TIME = 10;
	final int DEPARTURE_TIME = 10;
	final int CONFIRMATION_TIME = 10;
	final int QUEUE_TIME = 10;
	final int REFUSAL_TIME = 10;	
	
	/*
	 * Test 0: Declaring passenger object.
	 */
	Passenger passenger;
	
	/*
	 * Test 1: Declaring first object.
	 */
	First first;
	
	/*
	 * Test 2: Constructing a First object.
	 */
	@Before @Test public void setUpPassenger() throws PassengerException
	{
		first = new First(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("First patron is not flagged as new.", first.isNew(), true);
	}
	
	/*
	 * Test 3: Confirming a first class seat.
	 */
	@Test public void confirmASeat() throws PassengerException
	{
		first = new First(BOOKING_TIME, DEPARTURE_TIME);
		
		assertEquals("First patron should be new or queued.", first.isNew() || first.isQueued(), true);

		first.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);

		assertEquals("First patron should not be new or queued.", first.isNew() || first.isQueued(), false);
		assertEquals("First patron should be confirmed.", first.isConfirmed(), true);
		assertEquals("First patron confirmation time should be stored.",
				first.getConfirmationTime() == CONFIRMATION_TIME, true);
	}
	
	/*
	 * Test 4: Embark on a first class flight.
	 */
	@Test public void flyAway() throws PassengerException
	{
		first = new First(BOOKING_TIME, DEPARTURE_TIME);
		
		first.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		
		assertEquals("First patron should be confirmed.", first.isConfirmed(), true);
		
		first.flyPassenger(DEPARTURE_TIME);

		assertEquals("First patron should be flagged as flown.", first.isFlown(), true);
		assertEquals("First patron should have stored departure time.",
				first.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	/*
	 * Test 5: Queue a first passenger.
	 */
	@Test public void queueAPassenger() throws PassengerException
	{	
		first = new First(BOOKING_TIME, DEPARTURE_TIME);
	 
	 	assertEquals("First patron should be new.", first.isNew(), true);
		
	 	first.queuePassenger(QUEUE_TIME, DEPARTURE_TIME);
		
		assertEquals("First patron should now be queued.", first.isQueued(), true);
		assertEquals("First patron should have stored departure time.",
				first.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	
	/*
	 * Test 6: Refuse an first passenger.
	 */
	@Test public void refusePassenger() throws PassengerException
	{	
		first = new First(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("First patron should be new or queued.", first.isNew() || first.isQueued(), true);
		
	 	first.refusePassenger(REFUSAL_TIME);
		
		assertEquals("First patron should now be refused.", first.isRefused(), true);
	}
}
