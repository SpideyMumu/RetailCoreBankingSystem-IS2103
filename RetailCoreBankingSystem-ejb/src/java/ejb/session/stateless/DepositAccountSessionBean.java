/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author muhdm
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;
    
    @Override
    public Long createNewDepositAccount(DepositAccount acc) {
        em.persist(acc);
        em.flush();
        
        return acc.getDepositAccountId();
    }
    
    @Override
    public BigDecimal retrieveAvailableBalance (Long accId) {
        DepositAccount acc = em.find(DepositAccount.class, accId);
        return acc.getAvailableBalance();
    }
    
    @Override
    public List<DepositAccount> retrieveAllDepositAccount(Customer customer) {
        Query query = em.createQuery("SELECT d FROM DepositAccount d WHERE d.customer = :currCustomer");
        query.setParameter("currCustomer", customer);
        
        return query.getResultList();
    }
    
    @Override
    public void updateDepositAccount(DepositAccount acc) {
        em.merge(acc);
    }
 
}
