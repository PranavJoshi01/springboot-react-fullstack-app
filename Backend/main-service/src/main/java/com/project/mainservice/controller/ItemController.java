package com.project.mainservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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

    // 🔹 CREATE ITEM
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {

        // Extract logged-in username from JWT
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Item savedItem = itemService.createItem(item, username);
        return ResponseEntity.ok(savedItem);
    }

    // 🔹 GET ALL ITEMS
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // 🔹 GET ITEM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    // 🔹 UPDATE ITEM
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @RequestBody Item item) {

        Item updatedItem = itemService.updateItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }

    // 🔹 DELETE ITEM
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
