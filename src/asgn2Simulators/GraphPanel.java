package asgn2Simulators;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.*;
import org.jfree.data.category.DefaultCategoryDataset;

import asgn2Aircraft.AircraftException;

public class GraphPanel {

<<<<<<< HEAD
	/*
	 * Chart 1 datasets:
	 */
	DefaultCategoryDataset chart1Dataset;
=======
		
	//Chart1 Values
		DefaultCategoryDataset chart1Dataset;
		//--Chart 1 datasets-------------
		String totalEconomyLabel = "Econ";
		String totalPremiumLabel  = "Prem";
		String totalBusinessLabel  = "Bus";
		String totalFirstLabel  = "First";
		String totalTotalLabel  = "Total";
		String totalEmptyLabel  = "Empty";
		//--Chart 1 Graph Settings------
		String chart1Title = "Class Passengers vs Time";
		String chart1YAxis = "Number of Passengers";
		String chart1XAxis = "time";
		PlotOrientation chart1Orientation = PlotOrientation.VERTICAL;
		Boolean chart1Legend = true;
		Boolean chart1ToolTips = true;
		Boolean chart1URLS = false;
		
		int prevTotalEconomy = 0;
		int prevTotalPremium = 0;
		int prevTotalBusiness = 0;
		int prevTotalFirst = 0;
		int prevTotalBookings = 0;
		int prevTotalEmpty;
		
>>>>>>> 881cf87df4727238e792d766bc68fcd7d29d3277
	
	String totalEconomy = "Econ";
	String totalPremium = "Prem";
	String totalBusiness = "Bus";
	String totalFirst = "First";
	String totalTotal = "Total";
	String totalEmpty = "Empty";
	
	/*
	 * Chart 1 Graph Settings------
	 */
	String chart1Title = "Class Passengers vs Time";
	String chart1YAxis = "Number of Passengers";
	String chart1XAxis = "time";
	PlotOrientation chart1Orientation = PlotOrientation.VERTICAL;
	Boolean chart1Legend = true;
	Boolean chart1ToolTips = true;
	Boolean chart1URLS = false;
	

	/*
	 * Chart2 datasets
	 */
	DefaultCategoryDataset chart2Dataset;
	String queued = "Queue";
	String refused = "Refused";
	
	/*
	 * Chart 2 Graph settings
	 */
	String chart2Title = "Chart1";
	String chart2YAxis = "Number of Passengers";
	String chart2XAxis = "time";
	PlotOrientation chart2Orientation = PlotOrientation.VERTICAL;
	Boolean chart2Legend = true;
	Boolean chart2ToolTips = true;
	Boolean chart2URLS = false;		
	
	public GraphPanel() {
			
		chart1Dataset = new DefaultCategoryDataset();
		chart2Dataset = new DefaultCategoryDataset();
	}
	
<<<<<<< HEAD
	/**
	 * Updates datasest with simulator stats and the current time.
	 *
	 * @param sim <code>Simulator</code>.
	 * @param time <code>int</code>.
	 */
	public void UpdateChart( Simulator sim, int time) {
		
		String catTime = "" + time;
		
		//Add simulator values to chart 1 dataset.
		int totalBookings = sim.getTotalBusiness() + 
=======
	//Updates dataset with simulator stats and current time
	public void UpdateChart( Simulator sim, int time ){
		
		String catTime = "" + time;
		
		//Add simulator values to chart1 dataset
		
		int dayTotalBookings = sim.getTotalBusiness() + 
>>>>>>> 881cf87df4727238e792d766bc68fcd7d29d3277
								sim.getTotalEconomy() + 
								sim.getTotalFirst() + 
								sim.getTotalPremium() -
								prevTotalBookings;
		int dayTotalEconomy = sim.getTotalEconomy() - prevTotalEconomy;
		int dayTotalPremium = sim.getTotalPremium() - prevTotalPremium;
		int dayTotalBusiness = sim.getTotalEconomy() - prevTotalEconomy;
		int dayTotalFirst = sim.getTotalEconomy() - prevTotalEconomy;
		int dayTotalEmpty = sim.getTotalEmpty() - prevTotalEmpty;
		
		chart1Dataset.addValue(dayTotalEconomy, totalEconomyLabel, catTime);
		chart1Dataset.addValue(dayTotalPremium, totalPremiumLabel, catTime);
		chart1Dataset.addValue(dayTotalBusiness, totalBusinessLabel, catTime);
		chart1Dataset.addValue(dayTotalFirst, totalFirstLabel, catTime);
		chart1Dataset.addValue(dayTotalBookings, totalTotalLabel, catTime);
		chart1Dataset.addValue(dayTotalEmpty, totalEmptyLabel, catTime);
		
		//Add simulator values to chart2 dataset.
		chart2Dataset.addValue(sim.numInQueue(), queued, catTime);
		chart2Dataset.addValue(sim.numRefused(), refused, catTime);
		
		
		prevTotalEconomy = sim.getTotalEconomy();
		prevTotalPremium = sim.getTotalPremium();
		prevTotalBusiness = sim.getTotalBusiness();
		prevTotalFirst = sim.getTotalFirst();
		prevTotalBookings = prevTotalEconomy +
								prevTotalPremium +
								prevTotalBusiness +
								prevTotalFirst;
		prevTotalEmpty = sim.getTotalEmpty();
	}
	
	/**
	 * Generates the Class Passengers v. Time chart.
	 * 
	 * @return JFreeChart
	 */
	public JFreeChart GetChart1() {	
		return ChartFactory.createLineChart(chart1Title, chart1YAxis, chart1XAxis, 
				chart1Dataset, chart1Orientation, chart1Legend, chart1ToolTips, chart1URLS);
	}
	
	/**
	 * Generates the Total Passengers v. Time chart.
	 * 
	 * @return JFreeChart
	 */
	public JFreeChart GetChart2() {
		return ChartFactory.createLineChart(chart2Title, chart2YAxis, chart2XAxis, 
				chart2Dataset, chart2Orientation, chart2Legend, chart2ToolTips, chart2URLS);
	}
	
}
