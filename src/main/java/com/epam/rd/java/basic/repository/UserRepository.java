package com.epam.rd.java.basic.repository;

import com.epam.rd.java.basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByLogin(String login);
}
