package org.example.poo2_tf_jfx.model;
import javafx.geometry.Pos;
import org.example.poo2_tf_jfx.util.PostulationStatus;

import java.time.LocalDate;

public class Postulation {
    private User user;
    private JobVacancies jobVacancies;
    private PostulationStatus status;
    private LocalDate postulationDate;

    public Postulation(User user, JobVacancies jobVacancies, LocalDate postulationDate, PostulationStatus status) {
        this.user = user;
        this.jobVacancies = jobVacancies;
        this.postulationDate = LocalDate.now();
        this.status = status;
    }

    public void isPending(){
        status = PostulationStatus.POSTULADO;
    }

    public void isProcessed(){
        status = PostulationStatus.EN_GESTION;
    }

    public void approve(){
        status = PostulationStatus.ACEPTADO;
    }

    public void reject(){
        status = PostulationStatus.RECHAZADO;
    }

    public void approved(){
        status = PostulationStatus.ENTREVISTA;
    }

    public User getUser() {
        return user;
    }

    public JobVacancies getJobVacancies() {
        return jobVacancies;
    }

    public PostulationStatus getStatus() {
        return status;
    }

    public LocalDate getPostulationDate() {
        return postulationDate;
    }
}
