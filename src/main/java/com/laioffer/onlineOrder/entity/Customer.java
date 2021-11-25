package com.laioffer.onlineOrder.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 2652327633296064143L;

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;


    @OneToOne(cascade = CascadeType.ALL) //@OneToOne is just to define foreign key: cart is a FK to Customer
    @JoinColumn(unique = true) //guarantee values in Foreign key column are unique --> one customer to one cart; false by default
    private Cart cart; //not available when signUp

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Customer setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Customer setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
