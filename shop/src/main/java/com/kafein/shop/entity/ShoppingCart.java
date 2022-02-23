package com.kafein.shop.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ShoppingCart implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Item> items = new HashSet<>();
    
    public ShoppingCart() {
    }

    
    protected ShoppingCart(Long id, Set<Item> items) {
		this.id = id;
		this.items = items;
	}


	public ShoppingCart(Long id) {
		this.id = id;
	}


	public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
   

}
