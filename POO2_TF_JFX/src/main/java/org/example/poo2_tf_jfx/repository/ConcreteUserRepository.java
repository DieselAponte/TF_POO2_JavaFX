package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Profile;
import org.example.poo2_tf_jfx.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcreteUserRepository implements UserRepository {

    private final Connection connection;

    public ConcreteUserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) {
        try {
            if (user.getId() == 0) {
                // Insert
                String insertUser = "INSERT INTO users (name, email, password, profile_id) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                if (user.getProfile() != null) {
                    stmt.setInt(4, user.getProfile().getId());
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            } else {
                // Update
                String updateUser = "UPDATE users SET name = ?, email = ?, password = ?, profile_id = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(updateUser);
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                if (user.getProfile() != null) {
                    stmt.setInt(4, user.getProfile().getId());
                } else {
                    stmt.setNull(4, Types.INTEGER);
                }
                stmt.setInt(5, user.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(constructUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(constructUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(constructUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteUser = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteUser);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User constructUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        int profileId = rs.getInt("profile_id");

        Profile profile = null;
        if (profileId > 0) {
            profile = findProfileById(profileId);
        }

        return new User(id, name, email, password, profile);
    }

    private Profile findProfileById(int id) throws SQLException {
        String query = "SELECT * FROM profiles WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String profession = rs.getString("profession");
            return new Profile(profession, new ArrayList<>(), rs.getString("description"));
        }
        return null;
    }
}
