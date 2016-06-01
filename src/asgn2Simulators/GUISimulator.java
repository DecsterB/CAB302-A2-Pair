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
	private static final String PAGELIST_COMMAND = "page";
	private static final String SIMULATE_COMMAND = "simulate";
	
	//Panes and child objects.
	JLayeredPane viewPane, mainPane;
	JComboBox viewList, pageList;
	JTextField seedTextField, maxQueueTextField,
	meanBookTextField, bookSDTextField, minBookTextField, firstProbTextField,
	businessProbTextField, premiumProbTextField, ecoProbTextField,
	cancelProbTextField;
	
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
        String pageListOptions[] = {"Simulation", "Progress", "Summary"};
        pageList = new JComboBox(pageListOptions);
        pageList.setBounds(origin.x, origin.y, 200, 25);
        viewPane.add(pageList);
        
        pageList.setSelectedIndex(0);
        pageList.setActionCommand(PAGELIST_COMMAND);
        pageList.addActionListener(this);
        
        //Add the view pane to the window.
        getContentPane().add(viewPane);
	}
	
	public void createSimulationPane()
	{
		Point origin = new Point(15, 15);
        
		final int TEXT_WIDTH = 180;
		final int TEXT_HEIGHT = 25;
		
		//Create the simulation pane.
		mainPane = new JLayeredPane();
		mainPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		mainPane.setBorder(BorderFactory.createTitledBorder("Simulation: "));
		
		//Seed text box and label.
        JLabel seedLabel = new JLabel("Seed: ");
        seedLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(seedLabel);
        
        origin.y += TEXT_HEIGHT;
        
        seedTextField = new JTextField();
        seedTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(seedTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;

        //Maximum queue text box and label.
        JLabel maxQueueLabel = new JLabel("Max Queue Size: ");
        maxQueueLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(maxQueueLabel);
        
        origin.y += TEXT_HEIGHT;
        
        maxQueueTextField = new JTextField();
        maxQueueTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(maxQueueTextField);
        
        origin.y += TEXT_HEIGHT;
        origin.x = 15;
        
		//Daily booking mean text box and label.
        JLabel meanBookLabel = new JLabel("Daily Booking Mean: ");
        meanBookLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(meanBookLabel);
        
        origin.y += TEXT_HEIGHT;
        
        meanBookTextField = new JTextField();
        meanBookTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(meanBookTextField);

        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Daily booking SD text box and label.
        JLabel bookSDLabel = new JLabel("Daily Booking SD: ");
        bookSDLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(bookSDLabel);
        
        origin.y += TEXT_HEIGHT;
        
        bookSDTextField = new JTextField();
        bookSDTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(bookSDTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Daily minimum booking text box and label.
        JLabel minBookLabel = new JLabel("Daily Minimum Booking: ");
        minBookLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(minBookLabel);
        
        origin.y += TEXT_HEIGHT;
        
        minBookTextField = new JTextField();
        minBookTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(minBookTextField);
        
        origin.y += TEXT_HEIGHT;
        origin.x = 15;
        
		//First class probability text box and label.
        JLabel firstProbLabel = new JLabel("First Class Probability: ");
        firstProbLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(firstProbLabel);
        
        origin.y += TEXT_HEIGHT;
        
        firstProbTextField = new JTextField();
        firstProbTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(firstProbTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Business class probability text box and label.
        JLabel businessProbLabel = new JLabel("Business Class Probability: ");
        businessProbLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(businessProbLabel);
        
        origin.y += TEXT_HEIGHT;
        
        businessProbTextField = new JTextField();
        businessProbTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(businessProbTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Premium class probability text box and label.
        JLabel premiumProbLabel = new JLabel("Premium Class Probability: ");
        premiumProbLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(premiumProbLabel);
        
        origin.y += TEXT_HEIGHT;
        
        premiumProbTextField = new JTextField();
        premiumProbTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(premiumProbTextField);
        
        origin.y += TEXT_HEIGHT;
        origin.x = 15;
        
		//Economy class probability text box and label.
        JLabel ecoProbLabel = new JLabel("Economy Class Probability: ");
        ecoProbLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(ecoProbLabel);
        
        origin.y += TEXT_HEIGHT;
        
        ecoProbTextField = new JTextField();
        ecoProbTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(ecoProbTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Cancellation probability text box and label.
        JLabel cancelProbLabel = new JLabel("Cancellation Probability: ");
        cancelProbLabel.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(cancelProbLabel);
        
        origin.y += TEXT_HEIGHT;
        
        cancelProbTextField = new JTextField();
        cancelProbTextField.setBounds(origin.x, origin.y, TEXT_WIDTH, TEXT_HEIGHT);
        mainPane.add(cancelProbTextField);
        
        origin.y -= TEXT_HEIGHT;
        origin.x += TEXT_WIDTH;
        
		//Simulate button.
        JButton simulateButton = new JButton("Simulate!");
        simulateButton.setBounds(origin.x, origin.y, TEXT_WIDTH, 150);
        mainPane.add(simulateButton);  
        
        simulateButton.setActionCommand(SIMULATE_COMMAND);
        simulateButton.addActionListener(this);
        
        //Set initial values to defaults.
        setSimulationParameters();
        
        //Add main pane to window.
        getContentPane().add(mainPane);
	}
	
    /*
     * Component events.
     */
    public void actionPerformed(ActionEvent e)
    {
        String cmd = e.getActionCommand();
 
        if (cmd.contentEquals(VIEWLIST_COMMAND))
        {
        	int index = viewList.getSelectedIndex();
        	if (index == 0)
        	{
            	viewList.setBackground(Color.BLUE);        		
        	}
        	else if (index == 1)
        	{
            	viewList.setBackground(Color.GREEN);        		
        	}
        }
        else if (cmd.contentEquals(SIMULATE_COMMAND))
        {
        	//TODO: Get integers from the simulate page.
    		Simulator s = null;
    		Log l = null;
    		
			try
			{
				s = new Simulator();
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
		minBookTextField.setText(String.valueOf(Constants.MINIMUM_BOOKINGS));
		firstProbTextField.setText(String.valueOf(Constants.DEFAULT_FIRST_PROB));
		businessProbTextField.setText(String.valueOf(Constants.DEFAULT_BUSINESS_PROB));
		premiumProbTextField.setText(String.valueOf(Constants.DEFAULT_PREMIUM_PROB));
		ecoProbTextField.setText(String.valueOf(Constants.DEFAULT_ECONOMY_PROB));
		cancelProbTextField.setText(String.valueOf(Constants.DEFAULT_CANCELLATION_PROB));
	}
	
	public void setSimulationParameters(String[] args)
	{
		
	}
}
