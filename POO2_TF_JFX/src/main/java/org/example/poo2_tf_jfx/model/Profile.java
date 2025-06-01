package org.example.poo2_tf_jfx.model;
import java.util.List;
public class Profile {
    private int id;
    private int user_id;
    private String profession;
    private List<String> jobSkills;
    private String description;

    public Profile(String profession, List<String> jobSkills, String description) {
        this.profession = profession;
        this.jobSkills = jobSkills;
        this.description = description;
    }

    public void addSkill(String skill) {
        jobSkills.add(skill);
    }

    public boolean hasSkill(String skill) {
        return jobSkills.contains(skill);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public List<String> getJobSkills() {
        return jobSkills;
    }

    public void setJobSkills(List<String> jobSkills) {
        this.jobSkills = jobSkills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
