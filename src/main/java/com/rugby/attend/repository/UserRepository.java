package com.rugby.attend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rugby.attend.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByLineUserId(String lineUserId);
}