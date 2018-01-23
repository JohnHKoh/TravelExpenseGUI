/**
 * TripCost: Creates a GUI where the user enters multiple details about the trip, 
 * and those inputs will be used to calculate various expenses of the trip using
 * set reimbursement rates given by the problem.
 * Includes buttons to calculate or reset, and catches errors as well.
 * @author John Koh jhk5ec
 * @assignment HW5
 * @sources
 * http://stackoverflow.com/questions/1090098/newline-in-jlabel
   https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html
   http://stackoverflow.com/questions/6325384/adding-multiple-jpanels-to-jframe
   https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
   http://stackoverflow.com/questions/5064393/using-loop-to-get-values-from-jtextfields
   http://stackoverflow.com/questions/17777284/how-to-clear-text-fields-using-for-loop-in-java
   https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
   http://stackoverflow.com/questions/22452930/terminating-a-java-program
   http://stackoverflow.com/questions/6456219/java-checking-if-parseint-throws-exception
   https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
   http://stackoverflow.com/questions/726808/reset-remove-a-border-in-swing
   http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#button
   http://stackoverflow.com/questions/13334198/java-custom-buttons-in-showinputdialog
   http://stackoverflow.com/questions/18031704/jframe-how-to-disable-window-resizing
   http://www.w3schools.com/tags/att_font_size.asp
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;

public class TripCost extends JFrame {

	// Fields
	private JPanel panel;
	private JLabel prompt;
	private JLabel carText;
	private JButton calculate, reset;
	private JRadioButton rentalCar, privateCar;
	private ArrayList<JTextField> textFields;

	// Constants for reimbursement rates
	private final double MEALS_PAID = 37;
	private final double PARKING_PAID = 10;
	private final double TAXI_PAID = 20;
	private final double LODGING_PAID = 95;
	private final double MILES_PAID = 0.27;

	// Constants for window size
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 500;
	private Border defaultBorder;

	/** Constructor */
	public TripCost() {

		// Set the title.
		setTitle("Travel Expenses Calculator");

		// Specify what happens when the close button is clicked.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Build the panel that contains the other components.
		buildPanel();

		// Add the panel to the content pane.
		add(panel);

		// Size and display the window in the middle.  
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void buildPanel() {

		// Create labels to display on the form
		prompt = new JLabel("<html><center>"
				+ "<font size = \"5\">Welcome to the Travel Expenses Calculator!</font>"
				+ "<br><i>This program will calculate the total expenses, allowable expenses, "
				+ "<br>the amount needed to be paid, and the amount saved from a business trip.</i><br>"
				+ "<br>Enter the following information. "
				+ "<br>Enter \"0\" or leave the field blank if the charge does not apply."
				+ "</center></html>");
		carText = new JLabel("Number of miles:");
		String[] labels = {
				"Number of days on the trip:",
				"Airfare:",
				"Car type:",
				"Car fees:",
				"Parking fees:",
				"Taxi charges:",
				"Conference or seminar registration fees:",
				"Lodging charges:",
		        "Meal charges:"
				};

		// Create two radio buttons for user to choose either private car or radio car
		privateCar = new JRadioButton("Private Car");
		rentalCar = new JRadioButton("Rental Car");
		ButtonGroup group = new ButtonGroup();
		group.add(privateCar);
		group.add(rentalCar);
		privateCar.setSelected(true);
		privateCar.addActionListener(new ChangeCarType());
		rentalCar.addActionListener(new ChangeCarType());

		// Create an ArrayList of JTextFields to add all JTextFields to in order to access later 
		textFields = new ArrayList<JTextField>();

		// Create a components to click, a calculate button and reset button
		calculate = new JButton("Calculate");
		reset = new JButton("Reset");

		// Add action listeners to both buttons
		calculate.addActionListener(new CalcButtonListener());
		reset.addActionListener(new ResetButtonListener());

		// Create panels
		panel = new JPanel();
		JPanel header = new JPanel();                     // Create header panel
		header.add(prompt);                               // Add instructions to top panel
		panel.add(header);                                // Add panel to main panel
		JPanel inputs = new JPanel(new SpringLayout());   // Create panel of input text fields
		JPanel carChoice = new JPanel();                  // Create panel to hold radio buttons
		carChoice.add(privateCar);                        // Add car radio buttons 
		carChoice.add(rentalCar);
		for(int i = 0; i < labels.length; i++) {          // Add labels to input panel using for loop
			JLabel l = new JLabel(labels[i]);
			inputs.add(l);
			JTextField t = new JTextField(10);
			l.setLabelFor(t);
			textFields.add(t);
			if(i == 2) {                                  // For the third line of inputs, add the radio buttons
				inputs.add(carChoice);
				inputs.add(carText);
				inputs.add(t);
			}
			else {
				inputs.add(t);                            // Add all text fields to ArrayList to access later
			}
		}

		// Format input panel to look like a form with 1 label and 1 line each
		SpringUtilities.makeCompactGrid(inputs, labels.length + 1, 2, 6, 6, 6, 6);
		panel.add(inputs);

		// Add buttons to panel
		panel.add(reset);
		panel.add(calculate);

		// Get current default border to use later
		defaultBorder = textFields.get(0).getBorder();
	}

	/** ActionListener for "Calculate" button */
	private class CalcButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {


			/*
			 * In this for loop, each text field the program stored in the ArrayList earlier is checked to make sure 
			 * that if the input is empty, the program will input a default "0", and if the input is invalid, an error
			 * message will be shown with the appropriate error, and the first invalid text box will be highlighted red.
			 */
			for(JTextField tf : textFields) {

				// If box was highlighted on the first try, unhighlight it if the box is now correct
				tf.setBorder(defaultBorder);

				if(tf.getText().isEmpty()) // If input is empty...
					tf.setText("0");       // Set value to 0

				// If input is unable to be parsed as a Double, show error
				try {
					Double.parseDouble(tf.getText());

				} catch(NumberFormatException exc) { 
					JOptionPane.showMessageDialog(null, "One or more invalid inputs.", "Error", JOptionPane.ERROR_MESSAGE);
					tf.setBorder(BorderFactory.createLineBorder(Color.RED,2));
					return;

					// If input is negative, show error
				}
				if(Double.parseDouble(tf.getText()) < 0) { 
					JOptionPane.showMessageDialog(null, "Amount spent cannot be a negative value.", "Error", JOptionPane.ERROR_MESSAGE);
					tf.setBorder(BorderFactory.createLineBorder(Color.RED,2));
					return;
				}
			}

			// Check if days input is a whole number and able to be parsed as an Integer, otherwise, show error
			try {
				Integer.parseInt(textFields.get(0).getText());
			} catch(NumberFormatException exc) {
				JOptionPane.showMessageDialog(null, "Number of days must be a whole number.", "Error", JOptionPane.ERROR_MESSAGE);
				textFields.get(0).setBorder(BorderFactory.createLineBorder(Color.RED,2));
				return;
			}


			// Create new format to display pop-up box with expenses in readable dollar format
			DecimalFormat dollar = new DecimalFormat("#,##0.00");

			// Get values from the JTextFields or JRadioButtons
			int days = Integer.parseInt(textFields.get(0).getText());
			double airfare = Double.parseDouble(textFields.get(1).getText());
			double miles = Double.parseDouble(textFields.get(2).getText());
			double carFees = Double.parseDouble(textFields.get(3).getText());
			double parkingFees = Double.parseDouble(textFields.get(4).getText());
			double taxiCharges = Double.parseDouble(textFields.get(5).getText());
			double conferenceFees = Double.parseDouble(textFields.get(6).getText());
			double lodgingCharges = Double.parseDouble(textFields.get(7).getText());
			double mealCharges = Double.parseDouble(textFields.get(8).getText());

			// Calculate total expenses
			double totalExpenses = airfare + carFees + parkingFees + taxiCharges + conferenceFees + lodgingCharges + mealCharges;

			// Calculate reimbursement amounts, no money allotted if money not spent
			double parkingReimbursement = PARKING_PAID * days;
			if(parkingFees == 0) parkingReimbursement = 0;
			double taxiReimbursement = TAXI_PAID * days;
			if(taxiCharges == 0) taxiReimbursement = 0;
			double lodgingReimbursement = LODGING_PAID * days;
			if(lodgingCharges == 0) lodgingReimbursement = 0;
			double mealReimbursement = MEALS_PAID * days;
			double carReimbursement = 0;
			if(privateCar.isSelected()) {
				carReimbursement = MILES_PAID * miles; // Reimbursement for private car
			}
			else {
				carReimbursement = carFees;            // Reimbursement for rental car (entirely reimbursed)
			}
			// Airfare and conference fees are completely reimbursed, no rate calculations necessary

			// Calculate allowable expenses, or total money allotted to person by reimbursements
			double allowableExpenses = mealReimbursement + parkingReimbursement + taxiReimbursement + lodgingReimbursement + carReimbursement
					+ airfare + conferenceFees;

			// Calculate difference between the expenses and the allowable expenses to see how much the person owes
			double excess = 0;
			excess += subtractToZero(parkingFees,parkingReimbursement);
			excess += subtractToZero(taxiCharges,taxiReimbursement);
			excess += subtractToZero(mealCharges,mealReimbursement);
			excess += subtractToZero(lodgingCharges,lodgingReimbursement);
			excess += subtractToZero(carFees,carReimbursement);

			// Calculate the amount saved by the person
			double saved = 0;
			saved += subtractToZero(mealReimbursement,mealCharges);
			saved += subtractToZero(carReimbursement,carFees);
			// Other fields have the condition UP TO, so if the charge is less than the maximum allotted, then the whole charge is covered

			// Show pop-up displaying total expenses, allowable expenses, the excess needed to be paid, and the money saved
			String[] options = {"OK","More Information"};
			int result = JOptionPane.showOptionDialog(null, 
					"Total Expenses: $" + dollar.format(totalExpenses) + 
					"\nAllowable Expenses: $" + dollar.format(allowableExpenses) +
					"\nExcess Needed to be Paid: $" + dollar.format(excess) +
					"\nTotal Saved from Reimbursements: $" + dollar.format(saved),
					"Expenses",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.INFORMATION_MESSAGE,
					null, options,options[0]);
			if(result == JOptionPane.NO_OPTION) {
				JOptionPane.showMessageDialog(null, 
						"<html><i>Note: Reimbursements are the maximum allowed.<br><br></i></html>" +
								"\nAirfare: $" + dollar.format(airfare) +
								"\nAirfare Reimbursed: $" + dollar.format(airfare) +
								"\nCar Fees: $" + dollar.format(carFees) +
								"\nCar Fees Reimbursed: $" + dollar.format(carReimbursement) +
								"\nParking Fees: $" + dollar.format(parkingFees) +
								"\nParking Fees Reimbursed: $" + dollar.format(parkingReimbursement) +
								"\nTaxi Charges: $" + dollar.format(taxiCharges) +
								"\nTaxi Charges Reimbursed: $" + dollar.format(taxiReimbursement) +
								"\nConference Fees: $" + dollar.format(conferenceFees) +
								"\nConference Fees Reimbursed: $" + dollar.format(conferenceFees) +
								"\nLodging Charges: $" + dollar.format(lodgingCharges) +
								"\nLodging Charges Reimbursed: $" + dollar.format(lodgingReimbursement) +
								"\nMeal Charges: $" + dollar.format(mealCharges) +
								"\nMeal Charges Reimbursed: $" + dollar.format(mealReimbursement)
								,"Details",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	/** ActionListener for "Reset" button */
	private class ResetButtonListener implements ActionListener {

		// When reset button is pressed, clear all text fields
		public void actionPerformed(ActionEvent e) {
			for(JTextField tf : textFields) {
				tf.setText("");
			}
		}
	}


	// Grey out miles text field if a rental car is selected
	private class ChangeCarType implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == privateCar) {
				textFields.get(2).setText("");
				textFields.get(2).setEnabled(true);
				textFields.get(2).setBackground(Color.WHITE);
			}
			if(e.getSource() == rentalCar) {
				textFields.get(2).setText("0");
				textFields.get(2).setEnabled(false);
				textFields.get(2).setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	/**
	 * subtract two values, and if the result is less than 0, set the result to 0
	 * @param d1
	 * @param d2
	 * @return double of the resulting value
	 */
	private double subtractToZero(double d1, double d2) {
		double result = d1 - d2;
		if(result < 0)
			return 0;
		else return result;
	}


	// Main method to run program
	public static void main(String[] args) {
		TripCost tc = new TripCost();
	}
}
