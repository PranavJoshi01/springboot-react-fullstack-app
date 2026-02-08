package com.project.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.mainservice.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
