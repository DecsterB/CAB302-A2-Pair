package asgn2Tests;

import static org.junit.Assert.*;
import org.junit.*;

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Passengers.PassengerException;

/**
 * @author Matt Baker
 *
 */
public class A380Tests {

	int legalDepartureTime = 100;
	int legalBookingTime = 1;
	int legalconfirmationTime = 25;
	int legalCancellationTime = 50;
	String legalFlightCode = "TestFlightCode";
	int legalClassSize = 10;
	
	@Before
	public void setUp(){
	
		
		
	}
		
	//----Testing A380 Constructor-------------
	//Valid constructor
	@Test
	public void A380Constructor() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
				
		assertEquals(legalFlightCode, (String) testA380.getClass().getSuperclass().getDeclaredField("flightCode").get(testA380));
		assertEquals(legalDepartureTime, (int) testA380.getClass().getSuperclass().getDeclaredField("departureTime").get(testA380));
	}
	
	//Throws Exception for departureTime < 0
	@Test(expected=AircraftException.class)
	public void A380ConstructorNegativeDepartureTime() throws AircraftException { 
		new A380(legalFlightCode, -1);
	}
	
	//Throws Exception for departureTime = 0
	@Test(expected=AircraftException.class)
	public void A380ConstructorZeroDepartureTime() throws AircraftException { 
		new A380(legalFlightCode, 0);
	}
		
	//Throws Exception for empty flight code string
	@Test(expected=AircraftException.class)
	public void A380ConstructorEmptyFlightCode() throws AircraftException {
		new A380("", legalDepartureTime);
		
	}
	
	
	//----Testing A380 Constructor with class capacities------
	//Valid constructor
	@Test
	public void A380ConstructorValidClassCapacity() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime, legalClassSize, legalClassSize, legalClassSize, legalClassSize);
		
		assertEquals(legalClassSize, (int) testA380.getClass().getSuperclass().getDeclaredField("firstCapacity").get(testA380));
		assertEquals(legalClassSize, (int) testA380.getClass().getSuperclass().getDeclaredField("businessCapacity").get(testA380));
		assertEquals(legalClassSize, (int) testA380.getClass().getSuperclass().getDeclaredField("premiumCapacity").get(testA380));
		assertEquals(legalClassSize, (int) testA380.getClass().getSuperclass().getDeclaredField("economyCapacity").get(testA380));
		
	}
	
	//Throws exception for invalid first class capacity
	@Test(expected=AircraftException.class)
	public void A380ConstructorNegativeFirstClassCapacity() throws AircraftException { 
		new A380(legalFlightCode, legalDepartureTime, -1, legalClassSize, legalClassSize, legalClassSize);
	}
	
	
	//Throws exception for invalid business class capacity
	@Test(expected=AircraftException.class)
	public void A380ConstructorNegativeBusinessClassCapacity() throws AircraftException { 
		new A380(legalFlightCode, legalDepartureTime, legalClassSize, -1, legalClassSize, legalClassSize);
	}
	
	
	//Throws exception for invalid premium class capacity
	@Test(expected=AircraftException.class)
	public void A380ConstructorNegativePremiumClassCapacity() throws AircraftException { 
		new A380(legalFlightCode, legalDepartureTime, legalClassSize, legalClassSize, -1, legalClassSize);
	}
	
	
	//Throws exception for invalid economy class capacity
	@Test(expected=AircraftException.class)
	public void A380ConstructorNegativeEconomyClassCapacity() throws AircraftException { 
		new A380(legalFlightCode, legalDepartureTime, legalClassSize, legalClassSize, legalClassSize, -1);
	}
	
	
	
	
	
	
	//Test confirmBooking & cancel booking
	//Add booking then check its been added
	@Test
	public void A380TestBookings() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
		
		asgn2Passengers.Economy testPassenger = new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime);
		
		testA380.confirmBooking(testPassenger, legalconfirmationTime);
		
		assertTrue(testA380.hasPassenger(testPassenger));
		
		testA380.cancelBooking(testPassenger, legalCancellationTime);
		
		assertFalse(testA380.hasPassenger(testPassenger));
	}
	
	
	//Test getBookings
	//Add multiple bookings check added
	//--------------------------------------------------------------
	
	
	//Test flightEmpty
	@Test
	public void A380TestFlightEmpty() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
				
		assertTrue(testA380.flightEmpty());
	}
	
	@Test
	public void A380TestNotFlightEmpty() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
		
		testA380.confirmBooking(new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime), legalconfirmationTime);
		
		assertFalse(testA380.flightEmpty());
	}
	
	
	//Test flightFull
	@Test
	public void A380TestFlightFull() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
		
		//Set capacity of aircraft to 1
		testA380.getClass().getSuperclass().getDeclaredField("capacity").set(testA380, 1);
		
		testA380.confirmBooking(new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime), legalconfirmationTime);
		
		assertTrue(testA380.flightFull());
	}
	
	@Test
	public void A380TestNotFlightFull() throws AircraftException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
				
		assertFalse(testA380.flightFull());
		
		
	}
	
	
	//Test getPassengers
	//----------------------------------------------
	
	
	//Test hasPassenger
	@Test
	public void A380TestHasPassenger() throws AircraftException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime);
				
		asgn2Passengers.Economy testPassenger = new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime);
		
		assertFalse(testA380.hasPassenger(testPassenger));
		
		testA380.confirmBooking(testPassenger, legalconfirmationTime);
		
		assertTrue(testA380.hasPassenger(testPassenger));
	}
	
	
	//Test seatsAvailable
	@Test
	public void A380TestSeatsAvailable() throws AircraftException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime, 1, 1, 1, 1);
		
		asgn2Passengers.Economy testPassengerEconomy = new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime);
		asgn2Passengers.Premium testPassengerPremium = new asgn2Passengers.Premium(legalBookingTime, legalDepartureTime);
		asgn2Passengers.Business testPassengerBusiness = new asgn2Passengers.Business(legalBookingTime, legalDepartureTime);
		asgn2Passengers.First testPassengerFirst = new asgn2Passengers.First(legalBookingTime, legalDepartureTime);
		
		assertTrue(testA380.seatsAvailable(testPassengerEconomy));
		assertTrue(testA380.seatsAvailable(testPassengerEconomy));
		assertTrue(testA380.seatsAvailable(testPassengerEconomy));
		assertTrue(testA380.seatsAvailable(testPassengerEconomy));
		
		testA380.confirmBooking(testPassengerEconomy, legalconfirmationTime);
		testA380.confirmBooking(testPassengerPremium, legalconfirmationTime);
		testA380.confirmBooking(testPassengerBusiness, legalconfirmationTime);
		testA380.confirmBooking(testPassengerFirst, legalconfirmationTime);
		
		assertFalse(testA380.seatsAvailable(testPassengerEconomy));
		assertFalse(testA380.seatsAvailable(testPassengerPremium));
		assertFalse(testA380.seatsAvailable(testPassengerBusiness));
		assertFalse(testA380.seatsAvailable(testPassengerFirst));
	}
	
	//Test upgradeBookings
	//----------------------------------------------------------------------------
	//Add economy
	//upgradeBookings
	//Test passenger first
	
	//Test flyPassengers
	@Test
	public void A380TestFlyPassengers() throws AircraftException, PassengerException { 
		A380 testA380 = new A380(legalFlightCode, legalDepartureTime, 1, 1, 1, 1);
		
		asgn2Passengers.Economy testPassengerEconomy = new asgn2Passengers.Economy(legalBookingTime, legalDepartureTime);
		asgn2Passengers.Premium testPassengerPremium = new asgn2Passengers.Premium(legalBookingTime, legalDepartureTime);
		asgn2Passengers.Business testPassengerBusiness = new asgn2Passengers.Business(legalBookingTime, legalDepartureTime);
		asgn2Passengers.First testPassengerFirst = new asgn2Passengers.First(legalBookingTime, legalDepartureTime);
				
		testA380.confirmBooking(testPassengerEconomy, legalconfirmationTime);
		testA380.confirmBooking(testPassengerPremium, legalconfirmationTime);
		testA380.confirmBooking(testPassengerBusiness, legalconfirmationTime);
		testA380.confirmBooking(testPassengerFirst, legalconfirmationTime);
		
		testA380.flyPassengers(legalDepartureTime);
		
		assertTrue(testPassengerEconomy.isFlown());
		assertTrue(testPassengerPremium.isFlown());
		assertTrue(testPassengerBusiness.isFlown());
		assertTrue(testPassengerFirst.isFlown());
	}
}

	