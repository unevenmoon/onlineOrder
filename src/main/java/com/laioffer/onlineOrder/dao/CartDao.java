package com.laioffer.onlineOrder.dao;

import com.laioffer.onlineOrder.entity.Cart;
import com.laioffer.onlineOrder.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void removeCartItem(int orderItemId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            //this record is also used by cart -- List<OrderItem> orderItemList
            //thus, need to remove the reference in cart table first before deletion
            OrderItem orderItem = session.get(OrderItem.class, orderItemId);
            Cart cart = orderItem.getCart();
            cart.getOrderItemList().remove((orderItem));

            session.beginTransaction();
            session.delete(orderItem);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void removeAllCartItems(Cart cart) {
        for (OrderItem item : cart.getOrderItemList()) {
            removeCartItem(item.getId());
        }
    }
}
