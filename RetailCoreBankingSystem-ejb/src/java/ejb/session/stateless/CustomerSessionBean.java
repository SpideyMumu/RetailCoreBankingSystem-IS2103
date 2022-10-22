/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhdm
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewCustomer(Customer customer) {
        em.persist(customer);
        em.flush();
        
        return customer.getCustomerId();
    }
    
    @Override
    public Customer retrieveCustomerbyId(Long id) {
        Customer customer = em.find(Customer.class, id);
        return customer;
        //Have to implement exception error if customer does not exist
    }
    
    @Override
    public void updateCustomer(Customer customer) {
        em.merge(customer);
    }
    
}
