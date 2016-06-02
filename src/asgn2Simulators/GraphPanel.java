package asgn2Simulators;

import java.awt.Color;

import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraphPanel {

	
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
		String chart1Title = "Passengers vs Time by Class";
		String chart1YAxis = "Day";
		String chart1XAxis = "Number of Passengers";
		PlotOrientation chart1Orientation = PlotOrientation.VERTICAL;
		Boolean chart1Legend = true;
		Boolean chart1ToolTips = true;
		Boolean chart1URLS = false;
		
		int prevTotalEconomy = 0;
		int prevTotalPremium = 0;
		int prevTotalBusiness = 0;
		int prevTotalFirst = 0;
		int prevTotalBookings = 0;
		int prevTotalEmpty = 0;
		
	
	//Chart2 Values
		DefaultCategoryDataset chart2Dataset;
		//--Chart 2 datasets----------
		String queued = "Queue";
		String refused = "Refused";
		//--Chart 2 Graph settings----
		String chart2Title = "Number of Passengers Queued or Refused vs Time";
		String chart2YAxis = "Day";
		String chart2XAxis = "Number of Passengers";
		PlotOrientation chart2Orientation = PlotOrientation.VERTICAL;
		Boolean chart2Legend = true;
		Boolean chart2ToolTips = true;
		Boolean chart2URLS = false;




	
	public GraphPanel() {
			
		chart1Dataset = new DefaultCategoryDataset();
		chart2Dataset = new DefaultCategoryDataset();
	}
	
	/**
	 * Updates datasest with simulator stats and the current time.
	 *
	 * @param sim <code>Simulator</code>.
	 * @param time <code>int</code>.
	 */
	public void UpdateChart(Simulator sim, int time) {
		
		String catTime = "" + time;
		
		//Add simulator values to chart 1 dataset.
		int totalBookings = sim.getTotalBusiness() + 
								sim.getTotalEconomy() + 
								sim.getTotalFirst() + 
								sim.getTotalPremium() -
								prevTotalBookings;
		
		int dayTotalEconomy = sim.getTotalEconomy() - prevTotalEconomy;
		int dayTotalPremium = sim.getTotalPremium() - prevTotalPremium;
		int dayTotalBusiness = sim.getTotalBusiness() - prevTotalBusiness;
		int dayTotalFirst = sim.getTotalFirst() - prevTotalFirst;
		int dayTotalEmpty = sim.getTotalEmpty() - prevTotalEmpty;
		
		chart1Dataset.addValue(dayTotalEconomy, totalEconomyLabel, catTime);
		chart1Dataset.addValue(dayTotalPremium, totalPremiumLabel, catTime);
		chart1Dataset.addValue(dayTotalBusiness, totalBusinessLabel, catTime);
		chart1Dataset.addValue(dayTotalFirst, totalFirstLabel, catTime);
		chart1Dataset.addValue(totalBookings, totalTotalLabel, catTime);
		chart1Dataset.addValue(dayTotalEmpty, totalEmptyLabel, catTime);
		
		
		
		prevTotalEconomy = sim.getTotalEconomy();
		prevTotalPremium = sim.getTotalPremium();
		prevTotalBusiness = sim.getTotalBusiness();
		prevTotalFirst = sim.getTotalFirst();
		prevTotalEmpty = sim.getTotalEmpty();
		
		prevTotalBookings = prevTotalEconomy +
				prevTotalPremium +
				prevTotalBusiness +
				prevTotalFirst;
		

		//Add simulator values to chart2 dataset.
		chart2Dataset.addValue(sim.numInQueue(), queued, catTime);
		chart2Dataset.addValue(sim.numRefused(), refused, catTime);
	}
	

	/**
	 * Generates the Class Passengers v. Time chart.
	 * 
	 * @return JFreeChart
	 */
	public JFreeChart GetChart1(){	
		JFreeChart JFChart1 =  ChartFactory.createLineChart(chart1Title, chart1YAxis, chart1XAxis, 
				chart1Dataset, chart1Orientation, chart1Legend, chart1ToolTips, chart1URLS);
		
		CategoryPlot JFChartCatPlot = JFChart1.getCategoryPlot();
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		JFChartCatPlot.setRenderer(renderer);
		renderer.setShapesVisible(false);
		renderer.setSeriesPaint(0, Color.GRAY);
		renderer.setSeriesPaint(1, Color.CYAN);
		renderer.setSeriesPaint(2, Color.BLUE);
		renderer.setSeriesPaint(3, Color.BLACK);
		renderer.setSeriesPaint(4, Color.GREEN);
		renderer.setSeriesPaint(5, Color.RED);
		
		return JFChart1;
	}
	
	public JFreeChart GetChart2(){
		
		JFreeChart JFChart2 = ChartFactory.createLineChart(chart2Title, chart2YAxis, chart2XAxis, 
				chart2Dataset, chart2Orientation, chart2Legend, chart2ToolTips, chart2URLS);
		
		CategoryPlot JFChartCatPlot = JFChart2.getCategoryPlot();
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		JFChartCatPlot.setRenderer(renderer);
		renderer.setShapesVisible(false);
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setSeriesPaint(1, Color.RED);
		
		return JFChart2;
	}
	
	
}
