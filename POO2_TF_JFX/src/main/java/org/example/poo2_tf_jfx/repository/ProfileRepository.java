package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Profile;

import java.util.Optional;

public interface ProfileRepository {
    void save(Profile profile);
    Optional<Profile> findById(int id);
    Optional<Profile> findByUserId(int userId);
    void deleteById(int id);
}
