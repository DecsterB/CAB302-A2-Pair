package asgn2Tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import asgn2Simulators.GUISimulator;
import asgn2Simulators.Log;
import asgn2Simulators.SimulationException;
import asgn2Simulators.Simulator;

public class GUITests
{
	//Dummy test arguements.
	final String ARGUEMENT_ZERO = "WINDOW TITLE";
	
	/*
	 * Test 0: Declareing GUISimulator objects.
	 */
	GUISimulator gui;
	
	/*
	 * Test 1: Running a GUISimulator simulation.
	 */
	@Before @Test public void runASimulation() throws IOException, SimulationException
	{		
		Simulator s = new Simulator(); 		 
		Log l = new Log();

		gui.runSimulation(s, l);
	}	
}