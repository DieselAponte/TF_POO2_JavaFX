package org.example.poo2_tf_jfx.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobVacancies {
    private int id;
    private String title;
    private String description;
    private Company company;
    private LocalDate publicationDate;
    private List<String> jobRequirements;
    private Double salary;

    public JobVacancies(int id, String title, String description, Company company, LocalDate publicationDate, List<String> jobRequirements, double salary) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.publicationDate = LocalDate.now();
        this.jobRequirements = jobRequirements;
        this.salary = salary;
    }

    private boolean matchesKeyword(String keyword) {
        return title.contains(keyword) || description.contains(keyword);
    }

    private List<String> matchesSkills(List<String> skills) {
        List<String> matches = new ArrayList<>();
        for(String skill : skills) {
            for (String requirement : jobRequirements) {
                if(skill.equals(requirement)) {
                    matches.add(skill);
                }
            }
        }
        return matches;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<String> getJobRequirements() {
        return jobRequirements;
    }

    public void setJobRequirements(List<String> jobRequirements) {
        this.jobRequirements = jobRequirements;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
