package org.example.poo2_tf_jfx.repository;
import org.example.poo2_tf_jfx.model.*;
import java.util.List;
import java.util.Optional;

public interface JobVacancyRepository {
    void save(JobVacancies job);
    Optional<JobVacancies> findById(String id);
    List<JobVacancies> findByCompany(Company company);
    List<JobVacancies> findByKeyword(String keyword);
    List<JobVacancies> findByRequiredSkills(List<String> skills);
    List<JobVacancies> findAll();
    void deleteById(String id);
}

