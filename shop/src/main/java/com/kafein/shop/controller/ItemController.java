package com.kafein.shop.controller;

import com.kafein.shop.entity.Item;
import com.kafein.shop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    /*

    "Burada mutlu birkaç servis yazılı olduğunu hayal edelim" -Bob Ross

     */

    @GetMapping("/all")
    @ResponseBody
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
