/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import javax.ejb.Local;

/**
 *
 * @author muhdm
 */
@Local
public interface EmployeeSessionBeanLocal {
    
    public Long createNewEmployee(Employee employee);

    public Employee retrieveEmployeeById(Long i);
    
}
