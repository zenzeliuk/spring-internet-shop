package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Cart;
import com.epam.rd.java.basic.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i " +
            "where i.name like %:name% " +
            "and i.price between :priceFrom and :priceTo " +
            "and i.itemDetails.category.id in (:categoryIds) " +
            "and i.itemDetails.brand.id in (:brandIds) " +
            "and i.itemDetails.color.id in (:colorIds) " +
            "group by i")
    Page<Item> findAllWithFilter(
            Pageable pageable,
            @Param("name") String name,
            @Param("priceFrom") BigDecimal priceFrom,
            @Param("priceTo") BigDecimal priceTo,
            @Param("categoryIds") List<Long> listCategoryId,
            @Param("brandIds") List<Long> listBrandId,
            @Param("colorIds") List<Long> listColorId
    );


    /*
    @Query(value = "select * from items i, categories c, item_details it " +
            "where c.id=it.category_id and it.item_id=i.id and c.id in (:ids)", nativeQuery = true)
    Page<Item> findAllByCategoryIds(@Param("ids") List<Long> listCategory, Pageable pageable);

    @Query("select i from Item i, Category c, ItemDetails it " +
            "where c.id=it.category and i.itemDetails=it.id and c.id in (:ids) group by i")
    Page<Item> findAllByCategoryIds(@Param("ids") List<Long> listCategory, Pageable pageable);

    @Query("select i from Item i " +
            "where i.itemDetails.category.id in (:categoryIds) " +
            "group by i")
    Page<Item> findAllByCategoryIds(
            @Param("categoryIds") List<Long> listCategoryId,
            Pageable pageable
    );
*/

/*
 @Query("select i from Item i " +
         "where i.itemDetails.category.id in (:categoryIds) " +
         "and i.itemDetails.brand.id in (:brandIds) " +
         "group by i")
 Page<Item> findAllByCategoryAdnBrandIds(@Param("categoryIds") List<Long> listCategoryId,
                                         @Param("brandIds") List<Long> listBrandId,
                                         Pageable pageable);
*/

}
