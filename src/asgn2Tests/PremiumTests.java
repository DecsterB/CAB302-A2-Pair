/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

public class PremiumTests {
	final int BOOKING_TIME = 10;
	final int DEPARTURE_TIME = 10;
	final int CONFIRMATION_TIME = 10;
	final int QUEUE_TIME = 10;
	final int REFUSAL_TIME = 10;
	
	/*
	 * Test 0: Declaring premium object.
	 */
	Premium premium;
	
	/*
	 * Test 1: Constructing a Premium object.
	 */
	@Before @Test public void setUpPassenger() throws PassengerException {
		premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Premium patron is not flagged as new.", premium.isNew(), true);
	}
	
	/*
	 * Test 2: Confirming a premium class seat.
	 */
	@Test public void confirmASeat() throws PassengerException {
		assertEquals("Premium patron should be new or queued.", premium.isNew() || premium.isQueued(), true);

		premium.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);

		assertEquals("Premium patron should not be new or queued.", premium.isNew() || premium.isQueued(), false);
		assertEquals("Premium patron should be confirmed.", premium.isConfirmed(), true);
		assertEquals("Premium patron confirmation time should be stored.",
				premium.getConfirmationTime() == CONFIRMATION_TIME, true);
	}
	
	/*
	 * Test 3: Embark on a premium class flight.
	 */
	@Test public void flyAway() throws PassengerException {
		premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);
		
		premium.confirmSeat(CONFIRMATION_TIME, DEPARTURE_TIME);
		
		assertEquals("Premium patron should be confirmed.", premium.isConfirmed(), true);
		
		premium.flyPassenger(DEPARTURE_TIME);

		assertEquals("Premium patron should be flagged as flown.", premium.isFlown(), true);
		assertEquals("Premium patron should have stored departure time.",
				premium.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	/*
	 * Test 4: Queue a premium passenger.
	 */
	@Test public void queueAPassenger() throws PassengerException {	
		premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);
	 
	 	assertEquals("Premium patron should be new.", premium.isNew(), true);
		
	 	premium.queuePassenger(QUEUE_TIME, DEPARTURE_TIME);
		
		assertEquals("Premium patron should now be queued.", premium.isQueued(), true);
		assertEquals("Premium patron should have stored departure time.",
				premium.getDepartureTime() == DEPARTURE_TIME, true);
	}
	
	
	/*
	 * Test 5: Refuse a premium passenger.
	 */
	@Test public void refusePassenger() throws PassengerException {	
		premium = new Premium(BOOKING_TIME, DEPARTURE_TIME);

		assertEquals("Premium patron should be new or queued.", premium.isNew() || premium.isQueued(), true);
		
	 	premium.refusePassenger(REFUSAL_TIME);
		
		assertEquals("Economy patron should now be refused.", premium.isRefused(), true);
	}
}