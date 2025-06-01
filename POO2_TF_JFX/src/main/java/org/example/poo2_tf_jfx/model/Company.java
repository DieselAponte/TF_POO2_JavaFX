package org.example.poo2_tf_jfx.model;

public class Company {
    private int id;
    private String nameCompany;
    private String description;
    private String jobSector;
    private String companyAddress;

    public Company(int id,String nameCompany, String description, String jobSector, String companyAddress) {
        this.id = id;
        this.nameCompany = nameCompany;
        this.description = description;
        this.jobSector = jobSector;
        this.companyAddress = companyAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobSector() {
        return jobSector;
    }

    public void setJobSector(String jobSector) {
        this.jobSector = jobSector;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
}
