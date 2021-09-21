package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.ItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailsRepository extends JpaRepository<ItemDetails, Long> {

}
