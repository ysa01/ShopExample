package com.kafein.shop.service.implementation;

import com.kafein.shop.entity.Item;
import com.kafein.shop.entity.ShoppingCart;
import com.kafein.shop.repository.ShoppingCartRepository;
import com.kafein.shop.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Random;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;


    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart create(ShoppingCart newCart) {
        if (newCart.getId() == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }
      
        return shoppingCartRepository.saveAndFlush(newCart);
    }

    @Override
    public ShoppingCart addToCart(Long id, List<Item> newItems) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);
        for(Item news : newItems) {
            news.setCart(new ShoppingCart(id));
        }
        shoppingCart.getItems().addAll(newItems);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeFromCart(Long id, List<Item> newItems) {
        ShoppingCart query = shoppingCartRepository.findShoppingCartById(id);
        newItems.forEach(query.getItems()::remove);
        return shoppingCartRepository.save(query);
    }

    @Override
    public ShoppingCart getCart(Long id) {
        return shoppingCartRepository.findShoppingCartById(id);
    }
}
