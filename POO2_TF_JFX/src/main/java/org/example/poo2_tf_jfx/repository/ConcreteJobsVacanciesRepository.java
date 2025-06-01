package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Company;
import org.example.poo2_tf_jfx.model.JobVacancies;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ConcreteJobsVacanciesRepository implements JobVacancyRepository {

    private final Connection connection;

    public ConcreteJobsVacanciesRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(JobVacancies job) {
        try {
            String insertJob = "INSERT INTO job_vacancies (id, title, description, company_id, publication_date, salary) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertJob);
            stmt.setInt(1, job.getId());
            stmt.setString(2, job.getTitle());
            stmt.setString(3, job.getDescription());
            stmt.setInt(4, job.getCompany().getId());
            stmt.setDate(5, Date.valueOf(job.getPublicationDate()));
            stmt.setDouble(6, job.getSalary());
            stmt.executeUpdate();

            // Insertar requisitos
            String insertRequirement = "INSERT INTO job_requirements (job_vacancy_id, requirement) VALUES (?, ?)";
            for (String req : job.getJobRequirements()) {
                PreparedStatement reqStmt = connection.prepareStatement(insertRequirement);
                reqStmt.setInt(1, job.getId());
                reqStmt.setString(2, req);
                reqStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<JobVacancies> findById(String id) {
        try {
            String query = "SELECT * FROM job_vacancies WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(constructJobVacancy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<JobVacancies> findByCompany(Company company) {
        List<JobVacancies> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM job_vacancies WHERE company_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, company.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(constructJobVacancy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<JobVacancies> findByKeyword(String keyword) {
        List<JobVacancies> results = new ArrayList<>();
        try {
            String query = "SELECT * FROM job_vacancies WHERE title LIKE ? OR description LIKE ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            String like = "%" + keyword + "%";
            stmt.setString(1, like);
            stmt.setString(2, like);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(constructJobVacancy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<JobVacancies> findByRequiredSkills(List<String> skills) {
        List<JobVacancies> results = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT j.* FROM job_vacancies j " +
                    "JOIN job_requirements r ON j.id = r.job_vacancy_id " +
                    "WHERE r.requirement IN (%s)";

            String placeholders = String.join(",", Collections.nCopies(skills.size(), "?"));
            PreparedStatement stmt = connection.prepareStatement(String.format(sql, placeholders));

            for (int i = 0; i < skills.size(); i++) {
                stmt.setString(i + 1, skills.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(constructJobVacancy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public List<JobVacancies> findAll() {
        List<JobVacancies> results = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM job_vacancies");
            while (rs.next()) {
                results.add(constructJobVacancy(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void deleteById(String id) {
        try {
            String deleteRequirements = "DELETE FROM job_requirements WHERE job_vacancy_id = ?";
            PreparedStatement reqStmt = connection.prepareStatement(deleteRequirements);
            reqStmt.setInt(1, Integer.parseInt(id));
            reqStmt.executeUpdate();

            String deleteJob = "DELETE FROM job_vacancies WHERE id = ?";
            PreparedStatement jobStmt = connection.prepareStatement(deleteJob);
            jobStmt.setInt(1, Integer.parseInt(id));
            jobStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JobVacancies constructJobVacancy(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        int companyId = rs.getInt("company_id");
        LocalDate publicationDate = rs.getDate("publication_date").toLocalDate();
        double salary = rs.getDouble("salary");

        Company company = findCompanyById(companyId);
        List<String> requirements = findRequirementsByJobId(id);

        return new JobVacancies(id, title, description, company, publicationDate, requirements, salary);
    }

    private Company findCompanyById(int id) throws SQLException {
        String query = "SELECT * FROM company WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Company(
                    rs.getInt("id"),
                    rs.getString("name_company"),
                    rs.getString("description"),
                    rs.getString("job_sector"),
                    rs.getString("company_address")
            );
        }
        return null;
    }

    private List<String> findRequirementsByJobId(int jobId) throws SQLException {
        List<String> requirements = new ArrayList<>();
        String query = "SELECT requirement FROM job_requirements WHERE job_vacancy_id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, jobId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            requirements.add(rs.getString("requirement"));
        }
        return requirements;
    }
}
