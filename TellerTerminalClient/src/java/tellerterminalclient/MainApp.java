/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
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
    
    private void openDepositAccTerminal() {}
    
    private void issueAtmCardTerminal() {}
}
