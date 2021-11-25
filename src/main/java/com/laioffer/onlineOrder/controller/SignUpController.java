package com.laioffer.onlineOrder.controller;

import com.laioffer.onlineOrder.entity.Customer;
import com.laioffer.onlineOrder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

//annotation for DispatcherServlet to recognize
@Controller
public class SignUpController { //controller to define url
    @Autowired
    private CustomerService customerService;

    //annotation to define URL(value) and method in REST API
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody Customer customer) {
        //Customer to contain the data in request body
        customerService.signUp(customer);
    }
}
