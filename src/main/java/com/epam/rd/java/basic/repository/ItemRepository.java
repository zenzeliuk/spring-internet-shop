package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    /*
      @Query(value = "select * from items i, categories c, item_details it " +
              "where c.id=it.category_id and it.item_id=i.id and c.id in (:ids)", nativeQuery = true)
      Page<Item> findAllByCategoryIds(@Param("ids") List<Long> listCategory, Pageable pageable);
     */

    @Query("select i from Item i, Category c, ItemDetails it " +
            "where c.id=it.category and i.itemDetails=it.id and c.id in (:ids) group by i")
    Page<Item> findAllByCategoryIds(@Param("ids") List<Long> listCategory, Pageable pageable);

    Page<Item> findAll(Pageable pageable);
}
