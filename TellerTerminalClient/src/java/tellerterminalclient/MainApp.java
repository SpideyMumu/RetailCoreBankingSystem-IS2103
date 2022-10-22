/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import java.util.Scanner;

/**
 *
 * @author muhdm
 */
public class MainApp {
    private AtmCardSessionBeanRemote atmCardSB;
    private CustomerSessionBeanRemote customerSB;
    private EmployeeSessionBeanRemote employeeSB;

    public MainApp(AtmCardSessionBeanRemote atmCardSB, CustomerSessionBeanRemote customerSB, EmployeeSessionBeanRemote employeeSB) {
        this.atmCardSB = atmCardSB;
        this.customerSB = customerSB;
        this.employeeSB = employeeSB;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {            
            int response = 0;
            System.out.println("*** Welcome to Teller Terminal!***\n");
            System.out.println("1: Create Customer");
            System.out.println("2: Open Deposit Account");
            System.out.println("3: Issue ATM Card");
            System.out.println("4: Exit");
            
            response = sc.nextInt();
            if (response == 1) {
                createCustomerTerminal();
            } else if (response == 2) {
                openDepositAccTerminal();
            } else if (response == 3) {
                issueAtmCardTerminal();
            } else if (response == 4) {
                break;
            } else {
                System.out.println("Invalid Option! Please try Again");
            }
        }
    }
    
    private void createCustomerTerminal() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Teller Terminal :: Create New Staff ***\n");
        System.out.println("To create new customer, please fill in the details below:");
        
        System.out.print("Name:");
        String firstName = sc.next();
        String lastName = sc.next();
        System.out.println();
        
        sc.nextLine();
        System.out.print("ID number:");
        String idNum = sc.nextLine().trim();
        
        System.out.print("Contact Number:");
        String contactNum = sc.nextLine().trim();
        
        System.out.print("Address:");
        String address1 = sc.nextLine().trim();
        String address2 = "";
        
        //Split address into 2 if length of string > 255 characters
        if (address1.length() > 255) {
            int mid = address1.length() / 2;
            String[] address = {address1.substring(0, mid), address1.substring(mid)};
            address1 = address[0];
            address2 = address[1];
        }
        
        System.out.print("Postal Code:");
        String postalCode = sc.nextLine().trim();
        
        Customer newCustomer = new Customer(firstName, lastName, idNum, contactNum, address1, address2, postalCode);
        Long newCustomerId = customerSB.createNewCustomer(newCustomer);
        System.out.println("New Customer created successfully!: " + newCustomerId + "\n");
    }
    
    private void openDepositAccTerminal() {
    
    }
    
    private void issueAtmCardTerminal() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Teller Terminal :: Issue ATM Card ***\n");
        System.out.println("To issue ATM Card, a customer must be selected.");
        System.out.print("Enter Customer ID: "); //might need to change to retrieve by name or userName
        Long customerId = sc.nextLong();
        Customer customer = customerSB.retrieveCustomerbyId(customerId);
        sc.nextLine();
        
        if (customer.getAtmCard() == null) {
            System.out.println("Customer selected does NOT have an ATM Card!");
            System.out.println("Press any button to proceed to issue a new ATM Card...");
            sc.nextLine();
            //issue new card here
            issueNewAtmCard(customer);
        } else {
            System.out.println("Customer selected already has an ATM Card!");
            System.out.println("Would you like to issue a replacement? Y/N");
            String response = sc.nextLine();
            
            if (response.equalsIgnoreCase("Y")) {
                //issue replacement here
                issueReplacementAtmCard(customer, customer.getAtmCard());
            } else {
                System.out.println("Exiting from Issue ATM....");
            }
        }    
    }
    
    private void issueNewAtmCard(Customer customer) {
        Scanner sc = new Scanner(System.in);
        /*  1. Create new Atm Card
            2. Associate new Atm Card to Customer and vice versa
            3. Merge entities to update DB
        */
        System.out.println("Enter details of new card to proceed");
        AtmCard newCard = new AtmCard();
        customer.setAtmCard(newCard);
        newCard.setCustomer(customer);
        newCard.setEnabled(true);
        
        System.out.print("Card Number: ");
        String cardNumber = sc.nextLine().trim();
        newCard.setCardNumber(cardNumber);
        
        System.out.print("Name On Card: ");
        String nameOnCard = sc.nextLine().trim();
        newCard.setNameOnCard(nameOnCard);
        
        System.out.print("6-digits PIN: ");
        String pin = sc.nextLine().trim();
        newCard.setPin(pin);
        
//        customer.setAtmCard(newCard);
//        newCard.setCustomer(customer);
        
        //atmCardSB.createNewAtmCard(newCard);
        customerSB.updateCustomer(customer);
    }
    
    private void issueReplacementAtmCard(Customer customer, AtmCard currCard) {
        /*  1. Set current Atm Card as Disabled
            2. Disassociate current Atm Card
            3. Create new Atm Card
            4. Associate Customer to new ATM Card and vice versa
            5. merge entities to update DB
        */

        currCard.setEnabled(false);
        currCard.setCustomer(null);
        atmCardSB.updateAtmCard(currCard);
        issueNewAtmCard(customer);
    }
}
