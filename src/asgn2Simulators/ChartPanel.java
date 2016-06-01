package asgn2Simulators;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartPanel {

		
	//Chart1 Values
		DefaultCategoryDataset chart1Dataset;
		//--Chart 1 datasets-------------
		String totalEconomy = "Econ";
		String totalPremium = "Prem";
		String totalBusiness = "Bus";
		String totalFirst = "First";
		String totalTotal = "Total";
		String totalEmpty = "Empty";
		//--Chart 1 Graph Settings------
		String chart1Title = "Class Passengers vs Time";
		String chart1YAxis = "Number of Passengers";
		String chart1XAxis = "time";
		PlotOrientation chart1Orientation = PlotOrientation.VERTICAL;
		Boolean chart1Legend = true;
		Boolean chart1ToolTips = true;
		Boolean chart1URLS = false;
		
	
	//Chart2 Values
		DefaultCategoryDataset chart2Dataset;
		//--Chart 2 datasets----------
		String queued = "Queue";
		String refused = "Refused";
		//--Chart 2 Graph settings----
		String chart2Title = "Chart1";
		String chart2YAxis = "Number of Passengers";
		String chart2XAxis = "time";
		PlotOrientation chart2Orientation = PlotOrientation.VERTICAL;
		Boolean chart2Legend = true;
		Boolean chart2ToolTips = true;
		Boolean chart2URLS = false;
	
		
	
	public ChartPanel(){
			
		chart1Dataset = new DefaultCategoryDataset();
		chart2Dataset = new DefaultCategoryDataset();
	}
	
	//Updates dataset with simulator stats and current time
	public void UpdateChart( Simulator sim, int time){
		
		String catTime = "" + time;
		
		//Add simulator values to chart1 dataset
		int totalBookings = sim.getTotalBusiness() + 
								sim.getTotalEconomy() + 
								sim.getTotalFirst() + 
								sim.getTotalPremium();
		
		chart1Dataset.addValue(sim.getTotalEconomy(), totalEconomy, catTime);
		chart1Dataset.addValue(sim.getTotalPremium(), totalPremium, catTime);
		chart1Dataset.addValue(sim.getTotalBusiness(), totalBusiness, catTime);
		chart1Dataset.addValue(sim.getTotalFirst(), totalFirst, catTime);
		chart1Dataset.addValue(totalBookings, totalTotal, catTime);
		chart1Dataset.addValue(sim.getTotalEmpty(), totalEmpty, catTime);
		
		//Add simulator values to chart2 dataset
		chart2Dataset.addValue(sim.numInQueue(), queued, catTime);
		chart2Dataset.addValue(sim.numRefused(), refused, catTime);
	}
	
	public JFreeChart GetChart1(){	
		return ChartFactory.createLineChart(chart1Title, chart1YAxis, chart1XAxis, 
				chart1Dataset, chart1Orientation, chart1Legend, chart1ToolTips, chart1URLS);
	}
	
	public JFreeChart GetChart2(){
		return ChartFactory.createLineChart(chart2Title, chart2YAxis, chart2XAxis, 
				chart2Dataset, chart2Orientation, chart2Legend, chart2ToolTips, chart2URLS);
	}
	
}
