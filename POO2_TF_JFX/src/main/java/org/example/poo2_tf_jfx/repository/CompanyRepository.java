package org.example.poo2_tf_jfx.repository;

import org.example.poo2_tf_jfx.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {
    void save(Company company);
    Optional<Company> findById(int id);
    List<Company> findAll();
    void deleteById(int id);
}
