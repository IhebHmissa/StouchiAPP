package com.mycompany.myapp.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "History")
public class HistoryLine {

    @Id
    private String id;

    private String categoriName;
    private LocalDateTime dateModif;
    private float montant;
    private String userLogin;
    private String note; //Methode pour modifier Note /!\

    public HistoryLine() {}

    public HistoryLine(String categoriName, LocalDateTime dateModif, float montant, String userLogin, String note) {
        this.categoriName = categoriName;
        this.dateModif = dateModif;
        this.montant = montant;
        this.userLogin = userLogin;
        this.note = note;
    }

    public HistoryLine(String categoriName, LocalDateTime dateModif, float montant, String userLogin) {
        this.categoriName = categoriName;
        this.dateModif = dateModif;
        this.montant = montant;
        this.userLogin = userLogin;
    }

    public String getCategoriName() {
        return categoriName;
    }

    public void setCategoriName(String categoriName) {
        this.categoriName = categoriName;
    }

    public LocalDateTime getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return (
            "HistoryLine{" +
            "categoriName='" +
            categoriName +
            '\'' +
            ", dateModif=" +
            dateModif +
            ", montant=" +
            montant +
            ", userLogin='" +
            userLogin +
            '\'' +
            ", note='" +
            note +
            '\'' +
            '}'
        );
    }
}
