package com.project.mainservice.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.project.mainservice.model.Item;
import com.project.mainservice.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

 
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

   
    @PostMapping
    public Item createItem(@RequestBody Item item) {

        // Extract logged-in username from JWT
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return itemService.createItem(item, username);
    }

   
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

   
    @PutMapping("/{id}")
    public Item updateItem(
            @PathVariable Long id,
            @RequestBody Item item) {

        return itemService.updateItem(id, item);
    }

   
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}