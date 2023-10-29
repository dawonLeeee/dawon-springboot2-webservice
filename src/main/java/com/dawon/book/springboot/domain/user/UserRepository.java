package com.dawon.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);
}