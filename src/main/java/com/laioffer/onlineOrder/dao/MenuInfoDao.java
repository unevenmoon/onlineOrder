/*
restaurantDao + menuDao
 */
package com.laioffer.onlineOrder.dao;

import com.laioffer.onlineOrder.entity.MenuItem;
import com.laioffer.onlineOrder.entity.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository //tell Spring to create a Dao instance
public class MenuInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Restaurant> getRestaurants() {
        try (Session session = sessionFactory.openSession()) {
            //return a list of restaurant
            //need to input class type, --> .class is needed
            return session.createCriteria(Restaurant.class)//get a list from DB; left join by default --> will have duplicates
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)//dedup from left join
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>(); //if exception, return empty list
    }

    public List<MenuItem> getAllMenuItem(int restaurantId) {
        try (Session session = sessionFactory.openSession()) {
            //return single restaurant, no need to do DISTINCT
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);
            if (restaurant != null) {
                return restaurant.getMenuItemList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    public MenuItem getMenuItem(int menuItemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MenuItem.class, menuItemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
