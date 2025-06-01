package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.DirectMessage;

import java.util.List;
import java.util.Optional;

public interface DirectMessageRepository {
    void save(DirectMessage message);
    Optional<DirectMessage> findById(int id);
    List<DirectMessage> findAll();
    void deleteById(int id);
}
