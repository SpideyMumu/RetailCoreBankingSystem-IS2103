/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import java.util.Scanner;

/**
 *
 * @author muhdm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        
        System.out.println("*** Welcome to Automated Teller Machine!***\n");
        System.out.print("Please Enter your ATM Card Number: ");
        String cardNumber = sc.nextLine().trim();
        System.out.print("Please Enter your PIN: ");
        String pin = sc.nextLine().trim();
        
        //Retrieve ATM card entity here
        
    }
    
}
