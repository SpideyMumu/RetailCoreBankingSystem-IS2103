/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface DepositAccountSessionBeanLocal {
    
    public Long createNewDepositAccount(DepositAccount acc);
    
    public BigDecimal retrieveAvailableBalance(Long accId);
    
    public void updateDepositAccount(DepositAccount acc);
    
}
