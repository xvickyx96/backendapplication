package com.qussai.security.webSecurity.user;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  // Custom query to find a user by their email address.
  Optional<User> findByEmail(String email);

  public Optional<User> findById(Integer userId);
}
