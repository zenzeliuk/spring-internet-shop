package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
