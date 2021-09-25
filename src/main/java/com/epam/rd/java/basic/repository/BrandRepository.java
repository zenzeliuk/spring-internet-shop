package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("select b.id from Brand b")
    List<Long> findAllId();
}
