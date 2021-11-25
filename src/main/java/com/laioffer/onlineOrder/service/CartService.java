package com.laioffer.onlineOrder.service;

import com.laioffer.onlineOrder.dao.CartDao;
import com.laioffer.onlineOrder.entity.Cart;
import com.laioffer.onlineOrder.entity.Customer;
import com.laioffer.onlineOrder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartDao cartDao;

    public Cart getCart() {
        //steps to get a cart of current customer:
        //authentication, get customer by email, get cart by customer
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();

        Customer customer = customerService.getCustomer(email);
        Cart cart = customer.getCart();

        double totalPrice = 0;
        for (OrderItem item : cart.getOrderItemList()) {
            totalPrice += item.getPrice();
        }
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    public void cleanCart() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();

        Customer customer = customerService.getCustomer(email);
        Cart cart = customer.getCart();
        cartDao.removeAllCartItems(cart);
    }
}
