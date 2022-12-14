/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.DepositAccountType;

/**
 *
 * @author muhdm
 */
public class MainApp {
    private AtmCardSessionBeanRemote atmCardSB;
    private CustomerSessionBeanRemote customerSB;
    private EmployeeSessionBeanRemote employeeSB;
    private DepositAccountSessionBeanRemote depositAccountSB;

    public MainApp(AtmCardSessionBeanRemote atmCardSB, CustomerSessionBeanRemote customerSB, EmployeeSessionBeanRemote employeeSB, DepositAccountSessionBeanRemote depositAccountSB) {
        this.atmCardSB = atmCardSB;
        this.customerSB = customerSB;
        this.employeeSB = employeeSB;
        this.depositAccountSB = depositAccountSB;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {            
            int response;
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
        
        Customer newCustomer = new Customer();
        System.out.print("Name:");
//        String firstName = sc.next();
//        String lastName = sc.next();
        newCustomer.setFirstName(sc.next());
        newCustomer.setLastName(sc.next());
        
        sc.nextLine();
        System.out.print("ID number:");
//        String idNum = sc.nextLine().trim();
        newCustomer.setIdentificationNumber(sc.nextLine().trim());
        
        System.out.print("Contact Number:");
//        String contactNum = sc.nextLine().trim();
        newCustomer.setContactNumber(sc.nextLine().trim());
        
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
        newCustomer.setAddressLine1(address1);
        newCustomer.setAddressLine2(address2);
        
        System.out.print("Postal Code:");
//        String postalCode = sc.nextLine().trim();
        newCustomer.setPostalCode(sc.nextLine().trim());
        
        Long newCustomerId = customerSB.createNewCustomer(newCustomer);
        System.out.println("New Customer created successfully!: " + newCustomerId + "\n");
    }
    
    private void openDepositAccTerminal() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Teller Terminal :: Open Deposit Account ***\n");
        System.out.println("To open a Deposit Account, a customer must be selected.");
        System.out.print("Enter Customer ID: "); //might need to change to retrieve by name or userName
        Long customerId = sc.nextLong();
        Customer customer = customerSB.retrieveCustomerbyId(customerId);
        //List<DepositAccount> currListOfAccounts = depositAccountSB.retrieveAllDepositAccount(customerId);
        sc.nextLine();
        
        System.out.println("Enter details of new account to proceed");
        DepositAccount newAccount = new DepositAccount();
        
        //Association
        
        //does not work as fetch type is lazy
        //customer.getAccounts().size();
//        List<DepositAccount> updatedList = customer.getAccounts();
//        updatedList.add(newAccount);
//        customer.setAccounts(updatedList);
        newAccount.setCustomer(customer);
        
        //Set Account Details
        newAccount.setEnabled(true);
        newAccount.setAccountType(DepositAccountType.SAVINGS);
        
        System.out.print("Account Number: ");
        String accNum = sc.nextLine();
        newAccount.setAccountNumber(accNum);
        
        System.out.print("Enter Amount that Customer would like to deposit: ");
        BigDecimal amount = sc.nextBigDecimal();
        newAccount.setAvailableBalance(amount);
        newAccount.setHoldBalance(BigDecimal.ZERO);
        newAccount.setLedgerBalance(amount);
        depositAccountSB.createNewDepositAccount(newAccount);
        //customerSB.updateCustomer(customer);
        sc.nextLine();
        
        System.out.println("Kindly collect cash from Customer for deposit.");
        System.out.print("Press any button to proceed...");
        sc.nextLine();
    }
    
    
    
    private void issueAtmCardTerminal() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Teller Terminal :: Issue ATM Card ***\n");
        System.out.println("To issue ATM Card, a customer must be selected.");
        System.out.print("Enter Customer ID: "); //might need to change to retrieve by name or userName
        Long customerId = sc.nextLong();
        Customer customer = customerSB.retrieveCustomerbyId(customerId);
        sc.nextLine();
        
        if (customer.getAtmCard() == null) { //When customer has no ATM card
            System.out.println("Customer selected does NOT have an ATM Card!");
            System.out.println("Press any button to proceed to issue a new ATM Card...");
            sc.nextLine();
            //issue new card here
            issueNewAtmCard(customer);
        } else if (depositAccountSB.retrieveAllDepositAccount(customer).isEmpty()){ //When customer does not have deposit account
            System.out.println("Customer selected does NOT have a deposit account!");
            System.out.println("In order to issue an ATM Card, one must have at least one deposit account.");
            System.out.println("Please inform the customer you are serving to create an account first.");
            System.out.println("Exiting from Issue ATM....");
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
        //customer.setAtmCard(newCard);
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
        
        //Persiting ATM card and Associating ATM card to customer
        Long atmCardId = atmCardSB.createNewAtmCard(newCard);
        AtmCard currCard = atmCardSB.retrieveAtmCardByCardId(atmCardId);
        customer.setAtmCard(currCard);
        customerSB.updateCustomer(customer);
        /*
        Add deposit accounts
        */
        
        //List<DepositAccount> newListForAtmCard = new ArrayList<DepositAccount>();
        List<DepositAccount> currDepositAccounts = depositAccountSB.retrieveAllDepositAccount(customer);
        System.out.println("Select Deposit Account(s) you would like to associate with the new ATM Card: ");
        for (DepositAccount acc: currDepositAccounts) {
            System.out.println("Account is a " + acc.getAccountType().name() + " account. Account Number: " + acc.getAccountNumber());
            System.out.println("Would you like to link this Account? Y/N");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                //add to new list
                //newListForAtmCard.add(acc);
                acc.setAtmCard(currCard);
                depositAccountSB.updateDepositAccount(acc);
            }
        }
        //newCard.setAccounts(newListForAtmCard);
        //customerSB.updateCustomer(customer);
        System.out.println("Successfully Issued ATM Card!");
    }
    
    private void issueReplacementAtmCard(Customer customer, AtmCard currCard) {
        /*  1. Set current Atm Card as Disabled
            2. Disassociate current Atm Card
            3. Create new Atm Card
            4. Associate Customer to new ATM Card and vice versa
            5. merge entities to update DB
        */

        //dissociate atm card from customer
        currCard.setCustomer(null);
        atmCardSB.updateAtmCard(currCard); 
        
        //dissosiacte customer from atmCard
        customer.setAtmCard(null);
        customerSB.updateCustomer(customer);
         
        //dissoaciate deposit accounts
        List<DepositAccount> currDepositAccounts = depositAccountSB.retrieveAllDepositAccount(customer);
        
        for (DepositAccount acc : currDepositAccounts) {
            acc.setAtmCard(null);
            depositAccountSB.updateDepositAccount(acc);
        }
        
        //delete ATM card
        atmCardSB.deleteAtmCard(currCard.getAtmCardId());
        System.out.println("Successfully deleted previous ATM Card!");
        issueNewAtmCard(customer);
    }
}
