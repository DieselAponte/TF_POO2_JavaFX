package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcreteProfileRepository implements ProfileRepository {

    private final Connection connection;

    public ConcreteProfileRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Profile profile) {
        try {
            if (profile.getId() == 0) {
                String insertProfile = "INSERT INTO profiles (user_id, profession, description) VALUES (?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insertProfile, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, profile.getUser_id());
                stmt.setString(2, profile.getProfession());
                stmt.setString(3, profile.getDescription());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }

                saveSkills(profile);

            } else {
                String updateProfile = "UPDATE profiles SET user_id = ?, profession = ?, description = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(updateProfile);
                stmt.setInt(1, profile.getUser_id());
                stmt.setString(2, profile.getProfession());
                stmt.setString(3, profile.getDescription());
                stmt.setInt(4, profile.getId());
                stmt.executeUpdate();

                // Para simplificar, borramos todas las skills y las reinsertamos
                String deleteSkills = "DELETE FROM profile_skills WHERE profile_id = ?";
                PreparedStatement delStmt = connection.prepareStatement(deleteSkills);
                delStmt.setInt(1, profile.getId());
                delStmt.executeUpdate();

                saveSkills(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSkills(Profile profile) throws SQLException {
        if (profile.getJobSkills() != null) {
            String insertSkill = "INSERT INTO profile_skills (profile_id, skill) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertSkill);
            for (String skill : profile.getJobSkills()) {
                stmt.setInt(1, profile.getId());
                stmt.setString(2, skill);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    @Override
    public Optional<Profile> findById(int id) {
        try {
            String query = "SELECT * FROM profiles WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profile profile = constructProfile(rs);
                return Optional.of(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findByUserId(int userId) {
        try {
            String query = "SELECT * FROM profiles WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Profile profile = constructProfile(rs);
                return Optional.of(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) {
        try {
            String deleteSkills = "DELETE FROM profile_skills WHERE profile_id = ?";
            PreparedStatement delSkills = connection.prepareStatement(deleteSkills);
            delSkills.setInt(1, id);
            delSkills.executeUpdate();

            String deleteProfile = "DELETE FROM profiles WHERE id = ?";
            PreparedStatement delProfile = connection.prepareStatement(deleteProfile);
            delProfile.setInt(1, id);
            delProfile.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Profile constructProfile(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        String profession = rs.getString("profession");
        String description = rs.getString("description");

        List<String> skills = new ArrayList<>();
        String querySkills = "SELECT skill FROM profile_skills WHERE profile_id = ?";
        PreparedStatement stmtSkills = connection.prepareStatement(querySkills);
        stmtSkills.setInt(1, id);
        ResultSet rsSkills = stmtSkills.executeQuery();
        while (rsSkills.next()) {
            skills.add(rsSkills.getString("skill"));
        }

        Profile profile = new Profile(profession, skills, description);
        profile.setId(id);
        profile.setUser_id(userId);

        return profile;
    }
}
