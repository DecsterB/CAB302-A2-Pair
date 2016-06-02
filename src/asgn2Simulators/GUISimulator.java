/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import asgn2Simulators.GraphPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartPanel;

/**
 * @author Declan Barker
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable, ActionListener {
	//Frame Constants.
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;	

	private static final String WINDOW_TITLE = "Aircraft Bookings";
	
	//Action commands.
	private static final String PANELIST_COMMAND = "page";
	private static final String SIMULATE_COMMAND = "simulate";
	
	//Panes.
	private int currentPaneIndex;
	private static final String[] PaneNames = { "SIMULATION_PANE", "SUMMARY_PANE",
			"GRAPH_PANE_ONE", "GRAPH_PANE_TWO", "TEXT_OUTPUT_PANE" };
	JLayeredPane viewPane, mainPane, summaryPane, graphPane;
	
	//Simulation pane objects.
	JButton simulateButton;	
	JTextField seedTextField, maxQueueTextField,
	meanBookTextField, bookSDTextField, firstProbTextField,
	businessProbTextField, premiumProbTextField, ecoProbTextField,
	cancelProbTextField;
	
	//View pane objects.
	JComboBox viewList, paneList;
	
	//Summary pane children.
	JLabel ecoTotalLabel, businessTotalLabel, premiumTotalLabel,
	firstTotalLabel, emptyTotalLabel, flownTotalLabel;
	
	//Graph pane objects.
	ChartPanel cPanelOne, cPanelTwo;

	//Text output pane.
	JLayeredPane textOutputPane;
	JTextArea textOutputArea;
	JScrollPane textOutputScroll;
	
	GraphPanel g;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {		
		super(WINDOW_TITLE);
		
		g = new GraphPanel();
		currentPaneIndex = 0;
		createGUI();
	}
	
	public void createGUI() {	
		//Set behaviour for closing the window.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set the layout type for the window GUI.
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		//Set window width and height.
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		
        //Actually create the window components.
		createViewPane();
		createSimulationPane();
		createSummaryPane();
		createGraphPane();
		createTextOutputPane();
	}
	
	private void createViewPane() {
		Point origin = new Point(15, 15);
		
		//Create the view pane.
		viewPane = new JLayeredPane();
		viewPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 60));
		viewPane.setBorder(BorderFactory.createTitledBorder("View: "));
		
		//View label.
        JLabel viewLabel = new JLabel("View Mode:");
        viewLabel.setBounds(origin.x, origin.y, 100, 25);
        viewPane.add(viewLabel);
        
        origin.x += 100;        
        
        //Page combo box.
        String paneListOptions[] = {"Simulation", "Summary", "Graph One",
        		"Graph Two", "Text Output"};
        paneList = new JComboBox(paneListOptions);
        paneList.setBounds(origin.x, origin.y, 200, 25);
        viewPane.add(paneList);
        
        paneList.setSelectedIndex(0);
        paneList.setActionCommand(PANELIST_COMMAND);
        paneList.addActionListener(this);
        
        //Add the view pane to the window.
        getContentPane().add(viewPane);
	}
	
	private void createSimulationPane() {    
		final int ORIGIN_X = 15;
		final int ORIGIN_Y = 15;
		
		final int TEXT_WIDTH = 180;
		final int TEXT_HEIGHT = 25;
		
		final int PADDING_HOR = 15;
		final int PADDING_VER = 10;
		
		Rectangle bounds = new Rectangle(ORIGIN_X, ORIGIN_Y, TEXT_WIDTH, TEXT_HEIGHT);
		
		//Create the simulation pane.
		mainPane = new JLayeredPane();
		mainPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		mainPane.setBorder(BorderFactory.createTitledBorder("Simulation: "));
		
		//Seed text box and label.
        seedTextField = addLabelledInput(mainPane, "Seed: ", bounds);
        mainPane.add(seedTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);

        //Maximum queue text box and label.
        maxQueueTextField = addLabelledInput(mainPane, "Max Queue Size: ", bounds);
        mainPane.add(maxQueueTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);
        
		//Daily booking mean text box and label.
        meanBookTextField = addLabelledInput(mainPane, "Daily Booking Mean: ", bounds);
        mainPane.add(meanBookTextField);        
        bounds.setLocation(ORIGIN_X, bounds.y + TEXT_HEIGHT);
        
		//Daily booking SD text box and label.
        bookSDTextField = addLabelledInput(mainPane, "Daily Booking SD: ", bounds);
        mainPane.add(bookSDTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);
          
		//First class probability text box and label.
        firstProbTextField = addLabelledInput(mainPane, "First Class Probability: ", bounds);
        mainPane.add(firstProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);
        
		//Business class probability text box and label.
        businessProbTextField = addLabelledInput(mainPane, "Business Class Probability: ", bounds);
        mainPane.add(businessProbTextField);        
        bounds.setLocation(ORIGIN_X, bounds.y + TEXT_HEIGHT);
        
		//Premium class probability text box and label.
        premiumProbTextField = addLabelledInput(mainPane, "Premium Class Probability: ", bounds);
        mainPane.add(premiumProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);
        
		//Economy class probability text box and label.
        ecoProbTextField = addLabelledInput(mainPane, "Economy Class Probability: ", bounds);
        mainPane.add(ecoProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH + PADDING_HOR, bounds.y - TEXT_HEIGHT);
        
		//Cancellation probability text box and label.
        cancelProbTextField = addLabelledInput(mainPane, "Cancellation Probability: ", bounds);
        mainPane.add(cancelProbTextField);
        
		//Simulate button.
        simulateButton = new JButton("Simulate");
        simulateButton.setBounds(bounds.x,
        		bounds.y + TEXT_HEIGHT + PADDING_VER, TEXT_WIDTH, 125);
        mainPane.add(simulateButton);  
        
        simulateButton.setActionCommand(SIMULATE_COMMAND);
        simulateButton.addActionListener(this);
        
        //Set initial values to defaults.
        setSimulationParameters();
        
        //Add main pane to window.
        getContentPane().add(mainPane);
	}	
	
	private void createSummaryPane() {   
		final int ORIGIN_X = 15;
		final int ORIGIN_Y = 15;
		final int TEXT_WIDTH = 180;
		final int TEXT_HEIGHT = 25;
		Rectangle bounds = new Rectangle(ORIGIN_X, ORIGIN_Y, TEXT_WIDTH, TEXT_HEIGHT);
		
		//Create the simulation pane.
		summaryPane = new JLayeredPane();
		summaryPane.setVisible(false);
		summaryPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		summaryPane.setBorder(BorderFactory.createTitledBorder("Summary: "));

		ecoTotalLabel = addLabel(summaryPane, "Total Economy: ", bounds);
		businessTotalLabel = addLabel(summaryPane, "Total Business: ", bounds);
		premiumTotalLabel = addLabel(summaryPane, "Total Premium: ", bounds);
		
		bounds.setLocation(ORIGIN_X, bounds.y + TEXT_HEIGHT);
		
		firstTotalLabel = addLabel(summaryPane, "Total First: ", bounds);		
		emptyTotalLabel = addLabel(summaryPane, "Total Empty: ", bounds);
		flownTotalLabel = addLabel(summaryPane, "Flown Empty: ", bounds);
        
        //Add main pane to window.
        getContentPane().add(summaryPane);
	}
	
	private void createGraphPane() {		
		//Create the graph panel object.
		GraphPanel g = new GraphPanel();
		cPanelOne = new ChartPanel(g.GetChart1());
		cPanelTwo = new ChartPanel(g.GetChart2());

		cPanelOne.setVisible(false);
		cPanelTwo.setVisible(false);
		
		add(cPanelOne);
		add(cPanelTwo);
	}
	
	private void createTextOutputPane() {
		final int TEXT_AREA_WIDTH = 590;
		final int TEXT_AREA_HEIGHT = 350;
		Point origin = new Point(20, 20);
		
		//Create the view pane.
		textOutputPane = new JLayeredPane();
		textOutputPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		textOutputPane.setBorder(BorderFactory.createTitledBorder("Text Output: "));
		textOutputPane.setVisible(false);
		
		//Text area output.
        textOutputArea = new JTextArea("No data.");
        
		//Text area output.
        textOutputScroll = new JScrollPane(textOutputArea);
        textOutputScroll.setBounds(origin.x, origin.y,
        		TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);

        textOutputPane.add(textOutputScroll);
        
        //Add the view pane to the window.
        getContentPane().add(textOutputPane);
	}
	
	private void switchPane(String paneName, boolean visible) {
		switch (paneName) {
			case "SIMULATION_PANE":
	        	mainPane.setVisible(visible);
			break;
			
			case "SUMMARY_PANE":	
	        	summaryPane.setVisible(visible);
			break;			

			case "GRAPH_PANE_ONE":	
	        	cPanelOne.setVisible(visible);
			break;		
			
			case "GRAPH_PANE_TWO":	
				cPanelTwo.setVisible(visible);
			break;		
			
			case "TEXT_OUTPUT_PANE":	
				textOutputPane.setVisible(visible);
			break;			
			
			default:
				return;
		}
	}
	
    /*
     * Component events.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
 
        if (cmd.contentEquals(PANELIST_COMMAND)) {
        	//Get the index of the pane we are switching to.
        	int newIndex = paneList.getSelectedIndex();

        	//Hide pane at the index we are switching away from.
        	switchPane(PaneNames[currentPaneIndex], false);
        	currentPaneIndex = newIndex;
        	
        	//Switching to new pane.
        	switchPane(PaneNames[newIndex], true);  		
        }
        else if (cmd.contentEquals(SIMULATE_COMMAND)) {
    		Simulator s = null;
    		Log l = null;
    		
			try {
	        	//Get and verify simulation parameters from the simulate page.
				s = parseSimulationPage();
				
				if (s == null) {
					return;
				}
			}
			catch (SimulationException e1) {
				e1.printStackTrace();
			} 
			
			try {
				l = new Log();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}

			simulateButton.setEnabled(false);
			this.update(getGraphics());
			
    		this.runSimulation(s, l); 
    		
			simulateButton.setEnabled(true);
			
			setSummaryLabels(s);
			
			try {
				outputToTextArea(s);
			}
			catch (SimulationException e1) {
				e1.printStackTrace();
			}
        }
    }
    
    private void outputToTextArea(Simulator s) throws SimulationException {
    	String str = "";
    	
    	for (int i = Constants.FIRST_FLIGHT; i < Constants.DURATION; i++) {
    		str += i + ":" + " F:" + s.getFlightStatus(i).getNumFirst() +
    				" J:" + s.getFlightStatus(i).getNumBusiness() +
    				" P:" + s.getFlightStatus(i).getNumPremium() +
    				" Y:" + s.getFlightStatus(i).getNumEconomy() +
    				" T:" + s.getFlightStatus(i).getTotal() +
    				" A:" + s.getFlightStatus(i).getAvailable() + "\n\n";
    	}
    	
    	textOutputArea.setText(str);
    }
    
    /*
     * Takes the results of the latest simulation and applies it to the
     * graphs by regenerating them.
     */
    public void updateCharts(Simulator s, int time) {
		g.UpdateChart(s, time);
		
		cPanelOne = new ChartPanel(g.GetChart1());
		cPanelTwo = new ChartPanel(g.GetChart2());
		
		cPanelOne.setVisible(false);
		cPanelTwo.setVisible(false);
		
		add(cPanelOne);
		add(cPanelTwo);
    }
    
    private Simulator parseSimulationPage() throws SimulationException {
    	final double MIN_PROB = 0.0;
    	final double MAX_PROB = 1.0;
    	final int MIN_OTHER = 0;
    	final int MAX_OTHER = 50001;

    	Simulator s;    	
    	int seed = 0, maxQueue = 0;
    	double meanBook = 0.0, firstProb = 0.0, businessProb = 0.0,
    	bookSD = 0.0, premiumProb = 0.0, ecoProb = 0.0, cancelProb = 0.0;
    	
    	resetSimulationInputs();
    	
    	//Get values from GUI inputs.
    	seed = (int)verifySimulationInput(seedTextField, MIN_OTHER, MAX_OTHER);
    	maxQueue = (int)verifySimulationInput(maxQueueTextField, MIN_OTHER, MAX_OTHER);
    	meanBook = verifySimulationInput(meanBookTextField, MIN_OTHER, MAX_OTHER);
    	bookSD = verifySimulationInput(bookSDTextField, MIN_OTHER, MAX_OTHER);
    	
    	firstProb = verifySimulationInput(firstProbTextField, MIN_PROB, MAX_PROB);
    	businessProb = verifySimulationInput(businessProbTextField, MIN_PROB, MAX_PROB);
    	premiumProb = verifySimulationInput(premiumProbTextField, MIN_PROB, MAX_PROB);
    	ecoProb = verifySimulationInput(ecoProbTextField, MIN_PROB, MAX_PROB);
    	cancelProb = verifySimulationInput(cancelProbTextField, MIN_PROB, MAX_PROB);
    	
    	if (seed == -1 || maxQueue == -1 || meanBook == -1 || bookSD == -1 || firstProb == -1
    			|| businessProb == -1 || premiumProb == -1 || ecoProb == -1 || cancelProb == -1) {
    		JOptionPane.showMessageDialog(this, "Simulation parameters marked in red are not valid numbers.",
    				"Error: ", JOptionPane.ERROR_MESSAGE);
    		return null;
    	}
    	
    	//Set values for upcoming simulation.
    	s = new Simulator(seed, maxQueue, meanBook,  bookSD,
    			firstProb, businessProb, premiumProb, ecoProb, cancelProb);
    	
		return s;
    }
    
    /*
     * Resets the text colour after a Simulator input has been flagged as an error.
     */
    private void resetSimulationInputs() {
    	seedTextField.setForeground(Color.BLACK);
    	maxQueueTextField.setForeground(Color.BLACK);
    	meanBookTextField.setForeground(Color.BLACK);
    	bookSDTextField.setForeground(Color.BLACK);
    	firstProbTextField.setForeground(Color.BLACK);
    	businessProbTextField.setForeground(Color.BLACK);
    	premiumProbTextField.setForeground(Color.BLACK);
    	ecoProbTextField.setForeground(Color.BLACK);
    	cancelProbTextField.setForeground(Color.BLACK);
    }
    
    /*
     * Takes a single simulation input and checks to see if it is valid.
     */
    private double verifySimulationInput(JTextField textField,
    		double min, double max) {
    	double result = 0.0;
    	
    	//Parse integer from string check.
    	try {
    		result = Double.parseDouble(textField.getText());
    	}
    	catch (NumberFormatException e) {
    		textField.setForeground(Color.RED);
    		return -1.0;
    	}
    	
    	//Between min and max check.
    	if (result < min || result > max) {
    		textField.setForeground(Color.RED);
    		return -1.0;
    	}
    	
    	return result;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
        //Finalise window components.        
        pack();

        //Set window screen location.
		//Note: null is passed to setLocationRelativeTo because it has special case
		//logic for this parameter value that moves the window to the center of the screen.
        setLocationRelativeTo(null);
        
        //Reveal the window.
        setVisible(true);
	}
	
	/*
	 * Pre-condition: Validate user input values.
	 * Attempts to run the simulation with the values entered into the Simulator pane.
	 */
	public void runSimulation(Simulator sim, Log log) {				
		//Run the simulation 
		SimulationRunner sr = new SimulationRunner(sim, log);
		try {
			sr.runSimulation();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/*
	 * Sets the text fields in the simulation page to their default values.
	 */
	public void setSimulationParameters() {
		seedTextField.setText(String.valueOf(Constants.DEFAULT_SEED));
		maxQueueTextField.setText(String.valueOf(Constants.DEFAULT_MAX_QUEUE_SIZE));
		meanBookTextField.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_MEAN));
		bookSDTextField.setText(String.valueOf(Constants.DEFAULT_DAILY_BOOKING_SD));
		firstProbTextField.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
		businessProbTextField.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
		premiumProbTextField.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
		ecoProbTextField.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
		cancelProbTextField.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
	}
	
	/*
	 * Sets the text labels in the summary pane to their respective values.
	 */
	private void setSummaryLabels(Simulator s) {
		ecoTotalLabel.setText("Total Economy: " + String.valueOf(s.getTotalEconomy()));
		businessTotalLabel.setText("Total Business: " + String.valueOf(s.getTotalBusiness()));
		premiumTotalLabel.setText("Total Premium: " + String.valueOf(s.getTotalPremium()));
		firstTotalLabel.setText("Total First: " + String.valueOf(s.getTotalFirst()));
		emptyTotalLabel.setText("Total Empty: " + String.valueOf(s.getTotalEmpty()));
		flownTotalLabel.setText("Total Flown: " + String.valueOf(s.getTotalFlown()));		
	}
	
	/*
	 * Pane input helper methods.
	 */	
	private JTextField addLabelledInput(JLayeredPane pane, String labelText, Rectangle bounds) {
		JTextField textField;
		
        JLabel aLabel = new JLabel(labelText);
        aLabel.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        pane.add(aLabel);
        
        bounds.y += bounds.height;
        
        textField = new JTextField();
        textField.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        
        return textField;
	}
	
	private JLabel addLabel(JLayeredPane pane, String labelText, Rectangle bounds) {
        JLabel aLabel;
        aLabel = new JLabel(labelText);
        aLabel.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        pane.add(aLabel);

        bounds.x += bounds.width;
        
        return aLabel;
	}
}
