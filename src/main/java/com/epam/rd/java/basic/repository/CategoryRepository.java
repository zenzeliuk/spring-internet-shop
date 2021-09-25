package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.id from Category c")
    List<Long> findAllId();
}
