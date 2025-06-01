package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(int id);
}
