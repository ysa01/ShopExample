package com.kafein.shop.service;

import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart create(ShoppingCart newCart);
    ShoppingCart addToCart(Long id, List<Item> items);
    ShoppingCart removeFromCart(Long id, List<Item> items);
    ShoppingCart getCart(Long id);

}
