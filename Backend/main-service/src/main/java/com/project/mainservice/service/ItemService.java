package com.project.mainservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mainservice.model.Item;
import com.project.mainservice.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    // Constructor Injection (BEST PRACTICE)
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // CREATE
    public Item createItem(Item item, String username) {
        item.setCreatedBy(username);
        item.setCreatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

    // READ (ALL)
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // READ (BY ID)
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    // UPDATE
    public Item updateItem(Long id, Item updatedItem) {
        Item existingItem = getItemById(id);
        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        return itemRepository.save(existingItem);
    }

    // DELETE
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
