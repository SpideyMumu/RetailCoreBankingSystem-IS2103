/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import java.util.Scanner;

/**
 *
 * @author muhdm
 */
public class MainApp {
    private CustomerSessionBeanRemote customerSB;
    private AtmCardSessionBeanRemote atmCardSB;
    private DepositAccountSessionBeanRemote depositAccountSB;

    public MainApp(CustomerSessionBeanRemote customerSB, AtmCardSessionBeanRemote atmCardSB, DepositAccountSessionBeanRemote depositAccountSB) {
        this.customerSB = customerSB;
        this.atmCardSB = atmCardSB;
        this.depositAccountSB = depositAccountSB;
    }
    
    public void runApp() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Welcome to Automated Teller Machine! ***\n");
        System.out.print("Please Enter your ATM Card Number: ");
        String cardNumber = sc.nextLine().trim();
        
        AtmCard currCard = atmCardSB.retrieveAtmCardByCardNumber(cardNumber);
        System.out.println("*** Welcome " + currCard.getNameOnCard() + "! ***\n");
        
        while (true) {
            System.out.print("To proceed with any transactions, please enter your PIN:");
            //System.out.print("Please Enter your PIN: ");
            String pin = sc.nextLine().trim();

            if (!pin.equals(currCard.getPin())) {
                System.out.println("Wrong PIN entered! Please try again");
                System.out.println("If you would like to exit, type 'EXIT'. To try again, press any button");
                String response = sc.nextLine();
                
                if (response.equalsIgnoreCase("exit")) {
                    break;
                }
            } else {
                validATM(currCard);
            }
        }
    }
    
    private void validATM(AtmCard currCard) {
        Scanner sc = new Scanner(System.in);       
        System.out.println("1: Change PIN");
        System.out.println("2: Enquire Available Balance");
        System.out.println("3: Exit");
        int response = sc.nextInt();
        sc.nextLine();
        
        if (response == 1) {
                //createCustomerTerminal();
                System.out.print("Please Enter new 6-digit pin: ");
                String newPin = sc.nextLine().trim();
                System.out.print("Please enter the new PIN again:");
                
                if (sc.nextLine().trim().equals(newPin)) {
                    System.out.println("Your new PIN will be changed now.");
                    atmCardSB.changePin(newPin, currCard);
                    System.out.println("Your ATM card PIN has been successfully changed!");
                }
            } else if (response == 2) {
                doEnquireAvailableBalance();
            } else if (response == 3) {
            } else {
                System.out.println("Invalid Option! Please try Again");
            }
    }

    private void doEnquireAvailableBalance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
