package com.laioffer.onlineOrder.dao;

import com.laioffer.onlineOrder.entity.Authorities;
import com.laioffer.onlineOrder.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Repository is the same as @Component,
//but it indicates this class is for database communication
@Repository
public class CustomerDao { //customerDao to get info from front-end, and only do CRUD with DB
    //add SessionFactory as a dependency
    @Autowired
    private SessionFactory sessionFactory;

    public void signUp(Customer customer) {
        //get a session obj, and use methods to operate customer data -- CRUD
        Session session = null;
        Authorities authorities = new Authorities();
        authorities.setEmail(customer.getEmail());
        authorities.setAuthorities("ROLE_USER");//can be whatever, but need to match with the security check

        try {
            session = sessionFactory.openSession(); //get a session obj through sessionFactory
            session.beginTransaction(); //start transaction
            session.save(authorities); //save a row in authorities table
            session.save(customer); //save a row in customer table
            session.getTransaction().commit(); //write on disk
            //if exception happens, we can return to the stage before the transaction --> rollback() function in transaction

        } catch (Exception ex) {
            ex.printStackTrace();

            if (session != null) { //if session connection isself is failed, no need to rollback
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) { //after the operation, close the session to not occupy resource
                session.close();
            }
        }
    }

    public Customer getCustomer(String email) {
        Customer customer = null;
        Session session = null;
        try {
           session = sessionFactory.openSession();
           customer = session.get(Customer.class, email);
        } catch (Exception ex) {
           ex.printStackTrace();
           //we only read info here, no need to rollback
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;


    }
}
