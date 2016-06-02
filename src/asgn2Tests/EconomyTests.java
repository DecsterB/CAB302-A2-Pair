package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.Economy;
import asgn2Passengers.PassengerException;

public class EconomyTests
{
	final int BOOKING_TIME = 10;
	final int DEPARTURE_TIME = 10;
	final int CONFIRMATION_TIME = 10;
	final int QUEUE_TIME = 10;
	final int REFUSAL_TIME = 10;
	
	/*
	 * Test 0: Declaring Economy object.
	 */
	Economy economy;
	
	/*
	 * Test 1: Constructing a Economy object.
	 */
	@Before @Test public void setUpPassenger() throws PassengerException
	{
		economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Economy patron is not flagged as new.", economy.isNew(), true);
	}
	
	/*
	 * Test 2: Confirm a seat.
	 */
	@Test public void confirmASeat() throws PassengerException
	{
		assertEquals("Economy patron should be new or queued.", economy.isNew() || economy.isQueued(), true);

		economy.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);

		assertEquals("Economy patron should not be new or queued.", economy.isNew() || economy.isQueued(), false);
		assertEquals("Economy patron should be confirmed.", economy.isConfirmed(), true);
		assertEquals("Economy patron confirmation time should be stored.",
				economy.getConfirmationTime() == CONFIRMATION_TIME, true);
	}
	
	/*
	 * Test 3: Embark on a flight.
	 */
	@Test public void flyAway() throws PassengerException
	{
		economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);
		
		economy.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		
		assertEquals("Economy patron should be confirmed.", economy.isConfirmed(), true);
		
		economy.flyPassenger(DEPARTURE_TIME);

		assertEquals("Economy patron should be flagged as flown.", economy.isFlown(), true);
		assertEquals("Economy patron should have stored departure time.",
				economy.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	/*
	 * Test 4: Queue a passenger.
	 */
	@Test public void queueAPassenger() throws PassengerException
	{	
		economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);
	 
	 	assertEquals("Economy patron should be new.", economy.isNew(), true);
		
	 	economy.queuePassenger(QUEUE_TIME, DEPARTURE_TIME);
		
		assertEquals("Economy patron should now be queued.", economy.isQueued(), true);
		assertEquals("Economy patron should have stored departure time.",
				economy.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	
	/*
	 * Test 5: Refuse an economy passenger.
	 */
	@Test public void refusePassenger() throws PassengerException
	{	
		economy = new Economy(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Economy patron should be new or queued.", economy.isNew() || economy.isQueued(), true);
		
	 	economy.refusePassenger(REFUSAL_TIME);
		
		assertEquals("Economy patron should now be refused.", economy.isRefused(), true);
	}
}
