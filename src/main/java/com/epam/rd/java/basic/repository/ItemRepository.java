package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByItemDetails_Category_Id(Long categoryId);
}
