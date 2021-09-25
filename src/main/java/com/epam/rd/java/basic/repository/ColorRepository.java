package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {

    @Query("select c.id from Color c")
    List<Long> findAllId();
}
