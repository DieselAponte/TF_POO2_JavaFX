package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.DirectMessage;
import org.example.poo2_tf_jfx.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcreteDirectMessageRepository implements DirectMessageRepository {

    private final Connection connection;
    private final UserRepository userRepository; // Para buscar usuarios por id

    public ConcreteDirectMessageRepository(Connection connection, UserRepository userRepository) {
        this.connection = connection;
        this.userRepository = userRepository;
    }

    @Override
    public void save(DirectMessage message) {
        try {
            if (message.getId() == 0) {
                String insertSQL = "INSERT INTO direct_messages (user_sender_id, user_receiver_id, message, sending_date) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, message.getUserSender().getId());
                stmt.setInt(2, message.getUserReceiver().getId());
                stmt.setString(3, message.getMessage());
                stmt.setTimestamp(4, Timestamp.valueOf(message.getSendingDate()));
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    message.setId(keys.getInt(1));
                }
            } else {
                String updateSQL = "UPDATE direct_messages SET user_sender_id = ?, user_receiver_id = ?, message = ?, sending_date = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(updateSQL);
                stmt.setInt(1, message.getUserSender().getId());
                stmt.setInt(2, message.getUserReceiver().getId());
                stmt.setString(3, message.getMessage());
                stmt.setTimestamp(4, Timestamp.valueOf(message.getSendingDate()));
                stmt.setInt(5, message.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<DirectMessage> findById(int id) {
        try {
            String query = "SELECT * FROM direct_messages WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User sender = userRepository.findById(rs.getInt("user_sender_id")).orElse(null);
                User receiver = userRepository.findById(rs.getInt("user_receiver_id")).orElse(null);

                if (sender == null || receiver == null) {
                    return Optional.empty();
                }

                DirectMessage message = new DirectMessage(
                        rs.getInt("id"),
                        sender,
                        receiver,
                        rs.getString("message"),
                        rs.getTimestamp("sending_date").toLocalDateTime()
                );
                return Optional.of(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<DirectMessage> findAll() {
        List<DirectMessage> messages = new ArrayList<>();
        try {
            String query = "SELECT * FROM direct_messages";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                User sender = userRepository.findById(rs.getInt("user_sender_id")).orElse(null);
                User receiver = userRepository.findById(rs.getInt("user_receiver_id")).orElse(null);

                if (sender == null || receiver == null) continue;

                DirectMessage message = new DirectMessage(
                        rs.getInt("id"),
                        sender,
                        receiver,
                        rs.getString("message"),
                        rs.getTimestamp("sending_date").toLocalDateTime()
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public void deleteById(int id) {
        try {
            String sql = "DELETE FROM direct_messages WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
