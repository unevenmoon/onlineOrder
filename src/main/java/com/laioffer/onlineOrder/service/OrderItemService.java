package com.laioffer.onlineOrder.service;

import com.laioffer.onlineOrder.dao.OrderItemDao;
import com.laioffer.onlineOrder.entity.Customer;
import com.laioffer.onlineOrder.entity.MenuItem;
import com.laioffer.onlineOrder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service //to map menuItemId from front-end
public class OrderItemService {

    @Autowired //to get the customer
    private CustomerService customerService;

    @Autowired //to get the menu info for the menu field in Order
    private MenuInfoService menuInfoService;

    @Autowired
    private OrderItemDao orderItemDao;

    public void saveOrderItem(int menuId) {
        final OrderItem orderItem = new OrderItem();
        final MenuItem menuItem = menuInfoService.getMenuItem(menuId);

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String email = loggedInUser.getName();
        Customer customer = customerService.getCustomer(email);

        /*  to add the same order item and increase the quantity automatically
            customer.getCart().orderItemList();
            for loop the list
                if the item is already in the list, then count+=1 and update
         */

        orderItem.setMenuItem(menuItem);
        orderItem.setCart(customer.getCart());
        orderItem.setQuantity(1);
        orderItem.setPrice(menuItem.getPrice());
        orderItemDao.save(orderItem);

    }
}
