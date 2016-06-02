/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Simulators;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Declan Barker
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable, ActionListener
{
	//Frame Constants.
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = 480;	

	private static final String WINDOW_TITLE = "Aircraft Bookings";
	
	//Action commands.
	private static final String VIEWLIST_COMMAND = "view";
	private static final String PANELIST_COMMAND = "page";
	private static final String SIMULATE_COMMAND = "simulate";
	
	//Panes.
	private static final String[] PaneNames = { "SIMULATION_PANE", "PROGRESS_PANE", "SUMMARY_PANE" };
	private int currentPaneIndex;
	
	//Pane child objects
	JLayeredPane viewPane, mainPane, summaryPane;
	JComboBox viewList, paneList;
	JButton simulateButton;
	JTextField seedTextField, maxQueueTextField,
	meanBookTextField, bookSDTextField, firstProbTextField,
	businessProbTextField, premiumProbTextField, ecoProbTextField,
	cancelProbTextField;
	
	//Summary pane children.
	JLabel ecoTotalLabel, businessTotalLabel, premiumTotalLabel,
	firstTotalLabel, emptyTotalLabel;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException
	{
		super(WINDOW_TITLE);
		
		if (arg0 == null)
		{
			//TODO: Special case logic here.
		}

		currentPaneIndex = 0;
		createGUI();
	}
	
	public void createGUI()
	{	
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
	}
	
	public void createViewPane()
	{
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
        
        //View combo box.
        String viewListOptions[] = {"Details", "Graph"};
        viewList = new JComboBox(viewListOptions);
        viewList.setBounds(origin.x, origin.y, 100, 25);
        viewPane.add(viewList);

        viewList.setSelectedIndex(0);
        viewList.setActionCommand(VIEWLIST_COMMAND);
        viewList.addActionListener(this);
        
        origin.x += 100;
        
        //Page combo box.
        String paneListOptions[] = {"Simulation", "Progress", "Summary"};
        paneList = new JComboBox(paneListOptions);
        paneList.setBounds(origin.x, origin.y, 200, 25);
        viewPane.add(paneList);
        
        paneList.setSelectedIndex(0);
        paneList.setActionCommand(PANELIST_COMMAND);
        paneList.addActionListener(this);
        
        //Add the view pane to the window.
        getContentPane().add(viewPane);
	}
	
	private JTextField addLabelledInput(JLayeredPane pane, String labelText, Rectangle bounds)
	{
		JTextField textField;
		
        JLabel aLabel = new JLabel(labelText);
        aLabel.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        pane.add(aLabel);
        
        bounds.y += bounds.height;
        
        textField = new JTextField();
        textField.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        
        return textField;
	}
	
	private JLabel addLabel(JLayeredPane pane, String labelText, Rectangle bounds)
	{
        JLabel aLabel;
        aLabel = new JLabel(labelText);
        aLabel.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
        pane.add(aLabel);

        bounds.x += bounds.width;
        
        return aLabel;
	}
	
	public void createSummaryPane()
	{    
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
        
        //Add main pane to window.
        getContentPane().add(summaryPane);
	}
	
	public void createSimulationPane()
	{    
		final int ORIGIN_X = 15;
		final int ORIGIN_Y = 15;
		final int TEXT_WIDTH = 180;
		final int TEXT_HEIGHT = 25;
		Rectangle bounds = new Rectangle(ORIGIN_X, ORIGIN_Y, TEXT_WIDTH, TEXT_HEIGHT);
		
		//Create the simulation pane.
		mainPane = new JLayeredPane();
		mainPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		mainPane.setBorder(BorderFactory.createTitledBorder("Simulation: "));
		
		//Seed text box and label.
        seedTextField = addLabelledInput(mainPane, "Seed: ", bounds);
        mainPane.add(seedTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);

        //Maximum queue text box and label.
        maxQueueTextField = addLabelledInput(mainPane, "Max Queue Size: ", bounds);
        mainPane.add(maxQueueTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);
        
		//Daily booking mean text box and label.
        meanBookTextField = addLabelledInput(mainPane, "Daily Booking Mean: ", bounds);
        mainPane.add(meanBookTextField);        
        bounds.setLocation(ORIGIN_X, bounds.y + TEXT_HEIGHT);
        
		//Daily booking SD text box and label.
        bookSDTextField = addLabelledInput(mainPane, "Daily Booking SD: ", bounds);
        mainPane.add(bookSDTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);
          
		//First class probability text box and label.
        firstProbTextField = addLabelledInput(mainPane, "First Class Probability: ", bounds);
        mainPane.add(firstProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);
        
		//Business class probability text box and label.
        businessProbTextField = addLabelledInput(mainPane, "Business Class Probability: ", bounds);
        mainPane.add(businessProbTextField);        
        bounds.setLocation(ORIGIN_X, bounds.y + TEXT_HEIGHT);
        
		//Premium class probability text box and label.
        premiumProbTextField = addLabelledInput(mainPane, "Premium Class Probability: ", bounds);
        mainPane.add(premiumProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);
        
		//Economy class probability text box and label.
        ecoProbTextField = addLabelledInput(mainPane, "Economy Class Probability: ", bounds);
        mainPane.add(ecoProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y - TEXT_HEIGHT);
        
		//Cancellation probability text box and label.
        cancelProbTextField = addLabelledInput(mainPane, "Cancellation Probability: ", bounds);
        mainPane.add(cancelProbTextField);        
        bounds.setLocation(bounds.x + TEXT_WIDTH, bounds.y);
        
		//Simulate button.
        simulateButton = new JButton("Simulate");
        simulateButton.setBounds(bounds.x - (TEXT_WIDTH * 2), bounds.y + TEXT_HEIGHT, TEXT_WIDTH, 125);
        mainPane.add(simulateButton);  
        
        simulateButton.setActionCommand(SIMULATE_COMMAND);
        simulateButton.addActionListener(this);
        
        //Set initial values to defaults.
        setSimulationParameters();
        
        //Add main pane to window.
        getContentPane().add(mainPane);
	}
	
	private void setPaneVisible(String paneName, boolean hide)
	{
		switch (paneName)
		{
			case "SIMULATION_PANE":
	        	mainPane.setVisible(hide);   
			break;

			case "PROGRESS_PANE":	
	        	//mainPane.setVisible(hide);   
			break;
			
			case "SUMMARY_PANE":	
	        	summaryPane.setVisible(hide);   
			break;			
				
			default:
				//TODO: Throw an error here because we have
				//gone to an undefined pane.
			break;
		}
	}
	
	
	
    /*
     * Component events.
     */
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
 
        if (cmd.contentEquals(PANELIST_COMMAND))
        {
        	//Get the index of the pane we are switching to.
        	int newIndex = paneList.getSelectedIndex();

        	//Hide pane at the index we are switching away from.
        	setPaneVisible(PaneNames[currentPaneIndex], false);
        	currentPaneIndex = newIndex;
        	
        	//Switching to new pane.
        	setPaneVisible(PaneNames[newIndex], true);    		
        }
        else if (cmd.contentEquals(SIMULATE_COMMAND))
        {
    		Simulator s = null;
    		Log l = null;
    		
			try
			{
	        	//Get and verify simulation parameters from the simulate page.
				s = parseSimulationPage();
			}
			catch (SimulationException e1)
			{
				e1.printStackTrace();
			} 
			
			try
			{
				l = new Log();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			
    		this.runSimulation(s, l);
        }
    }
    
    private Simulator parseSimulationPage() throws SimulationException
    {
    	Simulator s;
    	int seed = 0, maxQueue = 0;
    	double meanBook = 0.0, firstProb = 0.0, businessProb = 0.0,
    	bookSD = 0.0, premiumProb = 0.0, ecoProb = 0.0, cancelProb = 0.0;
    	
    	//Get values from GUI inputs.
    	try
    	{
    		seed = Integer.parseInt(seedTextField.getText());
    		maxQueue = Integer.parseInt(maxQueueTextField.getText());
    		meanBook = Double.parseDouble(meanBookTextField.getText());
    		bookSD = Double.parseDouble(bookSDTextField.getText());
    		firstProb = Double.parseDouble(firstProbTextField.getText());
    		businessProb = Double.parseDouble(businessProbTextField.getText());
    		premiumProb = Double.parseDouble(premiumProbTextField.getText());
    		ecoProb = Double.parseDouble(ecoProbTextField.getText());
    		cancelProb = Double.parseDouble(cancelProbTextField.getText());
    	}
    	catch (NumberFormatException e)
    	{
    		//TODO: Throw here.
    		//return false;
    	}
    	
    	//Set values for upcoming simulation.
    	s = new Simulator(seed, maxQueue, meanBook,  bookSD,
    			firstProb, businessProb, premiumProb, ecoProb, cancelProb);
    	
		return s;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
        //Finalise window components.        
        pack();

        //Set window screen location.
		//Note: null is passed to setLocationRelativeTo because it has special case
		//logic for this parameter value that moves the window to the center of the screen.
        setLocationRelativeTo(null);
        
        //Reveal the window.
        setVisible(true);
	}
	
	public void runSimulation(Simulator sim, Log log)
	{
		simulateButton.setEnabled(false);
		this.update(getGraphics());
		
		//Run the simulation 
		SimulationRunner sr = new SimulationRunner(sim, log);
		try
		{
			sr.runSimulation();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		setSummaryLabels(sim);
		
		simulateButton.setEnabled(true);
	}

	/*
	 * Sets the text fields in the simulation page to their default values.
	 */
	public void setSimulationParameters()
	{
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
	 * Sets the text fields in the simulation page to their default values.
	 */
	private void setSummaryLabels(Simulator s)
	{
		ecoTotalLabel.setText("Total Economy: " + String.valueOf(s.getTotalEconomy()));
		businessTotalLabel.setText("Total Business: " + String.valueOf(s.getTotalBusiness()));
		premiumTotalLabel.setText("Total Premium: " + String.valueOf(s.getTotalPremium()));
		firstTotalLabel.setText("Total First: " + String.valueOf(s.getTotalFirst()));
		emptyTotalLabel.setText("Total Empty: " + String.valueOf(s.getTotalEmpty()));
	}
	
	public void setSimulationParameters(String[] args)
	{
		
	}
}
