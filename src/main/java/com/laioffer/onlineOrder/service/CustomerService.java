package com.laioffer.onlineOrder.service;

import com.laioffer.onlineOrder.dao.CustomerDao;
import com.laioffer.onlineOrder.entity.Cart;
import com.laioffer.onlineOrder.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService { //service layer - decide how to derive the data, and use the method defined in customerDao to communicate with DB
    private final CustomerDao customerDao;

    //constructor
    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void signUp(Customer customer) {
        //add cart into customer table as customerDao.signUp() did not provide this info
        //customerDao.signUp() only get info from fron-end, cart cannot be get from front-end
        Cart cart = new Cart();
        customer.setCart(cart);
        customer.setEnabled(true); //customer is in active status

        customerDao.signUp(customer);
    }


    public Customer getCustomer(String email) {
        return customerDao.getCustomer(email);
    }

}
