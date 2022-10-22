/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.EmployeeSessionBeanLocal;
import entity.Employee;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import util.enumeration.EmployeeAccessRightEnum;

/**
 *
 * @author muhdm
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBean;
    
    @PostConstruct
    public void postConstruct()
    {
        if (employeeSessionBean.retrieveEmployeeById(1l) == null) {
            initializeData();
        }
        
        /*try
        {
            employeeSessionBean.retrieveEmployeeById(1);
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        }*/
    }
    
    private void initializeData() {
        Employee newEmployee = new Employee("First", "Teller", "teller1", "password", EmployeeAccessRightEnum.TELLER);
        employeeSessionBean.createNewEmployee(newEmployee);
    }

    //Employee(String firstName, String lastName, String userName, String password, EmployeeAccessRightEnum employeeAccessRightEnum)
}
