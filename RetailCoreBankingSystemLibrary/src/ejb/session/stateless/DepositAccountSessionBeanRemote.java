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
import javax.ejb.Remote;

/**
 *
 * @author muhdm
 */
@Remote
public interface DepositAccountSessionBeanRemote {

    public Long createNewDepositAccount(DepositAccount acc);

    public BigDecimal retrieveAvailableBalance(Long accId);

    public void updateDepositAccount(DepositAccount acc);

    public List<DepositAccount> retrieveAllDepositAccount(Customer customer);
    
}
