package com.kafein.shop.service.implementation;

import com.kafein.shop.entity.Item;
import com.kafein.shop.repository.ItemRepository;
import com.kafein.shop.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

}
