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
	
	//Panes and child objects.
	JLayeredPane viewPane, mainPane;
	JComboBox viewList;
	JTextArea mainTextArea;
	
	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException
	{
		super(arg0);
		
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
		
		createViewPane();
		createMainPane();
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
        
        //Add the view pane to the window.
        getContentPane().add(viewPane);
	}
	
	public void createMainPane()
	{
		Point origin = new Point(15, 15);
        
		//Create the main pane.
		mainPane = new JLayeredPane();
		mainPane.setPreferredSize(new Dimension(WINDOW_WIDTH, 420));
		mainPane.setBorder(BorderFactory.createTitledBorder("Main: "));
		
		//Create main label.
        JLabel mainLabel = new JLabel("Output:");
        mainLabel.setBounds(origin.x, origin.y, 200, 25);
        mainPane.add(mainLabel);
        
        origin.y += 25;
        
        //Main combo box.
        mainTextArea = new JTextArea();
        mainTextArea.setBounds(origin.x, origin.y, 590, 320);
        //mainTextArea.setEnabled(false);
        
        mainPane.add(mainTextArea);

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

	/**
	 * @param args
	 * @throws SimulationException 
	 */
	public static void main(String[] args) throws SimulationException
	{
		GUISimulator gui = new GUISimulator(WINDOW_TITLE);
		
		gui.run();
		
		//SimulatorRunner sr = new SimulatorRunner();
		//int totalBusiness = sim.getTotalBusiness();
		
		//gui.mainTextArea.setText(Integer.toString(totalBusiness));
	}

}
