/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface CustomerSessionBeanLocal {
    
    public Long createNewCustomer(Customer customer);
    
    public Customer retrieveCustomerbyId(Long id);

    public void updateCustomer(Customer customer);
    
}
