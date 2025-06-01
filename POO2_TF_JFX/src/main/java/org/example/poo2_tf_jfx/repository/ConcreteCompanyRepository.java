package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConcreteCompanyRepository implements CompanyRepository {

    private final Connection connection;

    public ConcreteCompanyRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Company company) {
        try {
            if (company.getId() == 0) {
                String insertSQL = "INSERT INTO companies (name_company, description, job_sector, company_address) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, company.getNameCompany());
                stmt.setString(2, company.getDescription());
                stmt.setString(3, company.getJobSector());
                stmt.setString(4, company.getCompanyAddress());
                stmt.executeUpdate();

                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    company.setId(keys.getInt(1));
                }
            } else {
                String updateSQL = "UPDATE companies SET name_company = ?, description = ?, job_sector = ?, company_address = ? WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(updateSQL);
                stmt.setString(1, company.getNameCompany());
                stmt.setString(2, company.getDescription());
                stmt.setString(3, company.getJobSector());
                stmt.setString(4, company.getCompanyAddress());
                stmt.setInt(5, company.getId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Company> findById(int id) {
        try {
            String query = "SELECT * FROM companies WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Company company = new Company(
                        rs.getInt("id"),
                        rs.getString("name_company"),
                        rs.getString("description"),
                        rs.getString("job_sector"),
                        rs.getString("company_address")
                );
                return Optional.of(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        try {
            String query = "SELECT * FROM companies";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Company company = new Company(
                        rs.getInt("id"),
                        rs.getString("name_company"),
                        rs.getString("description"),
                        rs.getString("job_sector"),
                        rs.getString("company_address")
                );
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void deleteById(int id) {
        try {
            String sql = "DELETE FROM companies WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
