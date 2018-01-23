 --------------------------
|Travel Expenses Calculator|
 --------------------------
Created by John Koh jhk5ec

The Travel Exepenses Calculator is a program that takes user inputs to calculate
the total expenses incurred by the trip and the allowable expenses determined by
set reimbursement rates. In addition, the program also displays the excess that 
must be paid and the money saved by the trip taker. More details below...

The Travel Expenses Calculator takes the following inputs:
	- (int) Number of days on the trip
	- (double) Airfare
	- (JRadioButton inpts) Car type - private or rental
	- (double) Miles driven - only enabled if car type is private
	- (double) Car fees
	- (double) Parking fees
	- (double) Taxi charges
	- (double) Conference or seminar registration fees
	- (double) Lodging charges
	- (double) Meal charges

Note: Car fees can mean two things in two different contexts. For a private car,
      car fees is the money spent for gas and car maintenance, and for a rental car,
      car fees is the rental car fee.

Reimbursements are calculated according to the following policy:
	- Airfare - no cap, entirely reimbursed
	- Car fees - for rental car, no cap
		   - for private car, $0.27 per mile driven
	- Parking fees - up to $10.00 per day
	- Taxi charges - up to $20.00 per day
	- Conference or seminar registration fees - no cap
	- Lodging charges - up to $95.00 per day
	- Meal charges - $37 per day

Note: Reimbursements are only given if the money is spent for the specific carge!
      So for example, if no parking fees are incurred on the trip, then no money will be given. 
      As another example, money reimbursed for taxi charges cannot be towards lodging charges.
      THE EXCEPTION to this rule is the $37 per day for meal charges. That money is simply extra
      money allotted to the person, even if the meal charge is less than $37 per day.

The program outputs the following information in a popup:
	- Total expenses - the total for all fees and charges
	- Allowable expenses - the amount reimbursed by the company
	- Excess needed to be paid - the amount that certain charges went over reimbursement rates
	- Total saved from reimbursements - the amount that certain charges were under allotted rates

Note: The excess needed to be paid does not also factor in the amount saved, meaning if the excess is
      $20 and the amount saved is $11, then the excess does not adjust to $9. 
      Also, the total saved is the running sum of each amount saved from the individual charges and 
      reimbursements. 
      [[IMPORTANT]] The only money that can be saved from reimbursements is the private car reimbursements
      and the meal charges reimbursements.
      This is because the other reimbursements are either completely reimbursed, such as airfare 
      and rental car fees, or they include the condition "up to," meaning that if the charge is less than 
      the maximum reimbursement possible, then extra money will not be given and therefore not saved.

Extra features:
	- Nicely formatted instructions
	- Uses SpringUtilities.java (Copyright (c) 1995, 2008, Oracle) to make form layout
	- Reset button
	- Catches invalid inputs and highlights the first occurrence of an invalid input in red
	- Outputs error messages for invalid inputs
	- Not resizable, so the layout will not be able to be messed up
	- Option for "More Information" after calculating total expenses
		> shows each individual charge
		> shows the MAXIMUM possible reimbursement for a charge, which is not necessarily
		  the reimbursement that was given
	- If rental car radio button is selected, then the miles text field is greyed out

Included files:
	- TripCost.java - the program
	- SpringUtilities.java - includes SpringLayout and makeCompactGrid
	- README.txt - explains program and affiliated files