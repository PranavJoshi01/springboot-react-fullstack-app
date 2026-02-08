package com.project.mainservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.mainservice.model.Item;
import com.project.mainservice.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

 
    public Item createItem(Item item, String username) {
        item.setCreatedBy(username);
        item.setCreatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

  
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found with id: " + id)
                );
    }


    public Item updateItem(Long id, Item updatedItem) {
        Item existingItem = getItemById(id);

       
        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }

        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }

        return itemRepository.save(existingItem);
    }

   
    public void deleteItem(Long id) {
        Item existingItem = getItemById(id);
        itemRepository.delete(existingItem);
    }
}
