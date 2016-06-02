package asgn2Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.PassengerException;

public class BusinessTests
{
	//Dummy constants.
	final int BOOKING_TIME = 10;
	final int DEPARTURE_TIME = 10;
	final int CONFIRMATION_TIME = 10;
	final int QUEUE_TIME = 10;
	
	/*
	 * Test 0: Declaring business object.
	 */
	Business business;
	
	/*
	 * Test 1: Constructing a Business object.
	 */
	@Before @Test public void setUpPassenger() throws PassengerException
	{
		business = new Business(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Business patron is not flagged as new.", business.isNew(), true);
	}
	
	/*
	 * Test 2: Confirming a business class seat.
	 */
	@Test public void confirmASeat() throws PassengerException
	{
		assertEquals("Business patron should be new or queued.", business.isNew() || business.isQueued(), true);

		business.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);

		assertEquals("Business patron should not be new or queued.", business.isNew() || business.isQueued(), false);
		assertEquals("Business patron should be confirmed.", business.isConfirmed(), true);
		assertEquals("Business patron confirmation time should be stored.",
				business.getConfirmationTime() == CONFIRMATION_TIME, true);
	}
	
	/*
	 * Test 3: Embark on a business class flight.
	 */
	@Test public void flyAway() throws PassengerException
	{
		assertEquals("Business patron should be confirmed.", business.isConfirmed(), true);
		
		business.flyPassenger(DEPARTURE_TIME);

		assertEquals("Business patron should be flagged as flown.", business.isFlown(), true);
		assertEquals("Business patron should have stored departure time.",
				business.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	/*
	 * Test 4: Queue a business passenger.
	 */
	@Test public void queueAPassenger() throws PassengerException
	{	
		business = new Business(BOOKING_TIME, DEPARTURE_TIME);
	 
	 	assertEquals("Business patron should be new.", business.isNew(), true);
		
	 	business.queuePassenger(QUEUE_TIME, DEPARTURE_TIME);
		
		assertEquals("Business patron should now be queued.", business.isNew(), true);
		assertEquals("Business patron should have stored departure time.",
				business.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	
	/*
	 * Test 5: Refuse an business passenger.
	 */
	@Test public void refusePassenger() throws PassengerException
	{	
		business = new Business(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Business patron should be new or queued.", business.isNew() || business.isQueued(), true);
		
	 	business.queuePassenger(QUEUE_TIME, DEPARTURE_TIME);
		
		assertEquals("Business patron should now be refused.", business.isRefused(), true);
	}
}
