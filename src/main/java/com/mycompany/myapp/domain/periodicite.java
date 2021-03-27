package com.mycompany.myapp.domain;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "periodicite")
public class periodicite {

    @Id
    private String ID;

    private LocalDateTime date_deb;
    private LocalDateTime date_fin;
    private String frequancy;

    public periodicite() {}

    public periodicite(LocalDateTime date_deb, LocalDateTime date_fin, String frequancy) {
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.frequancy = frequancy;
    }

    @Override
    public String toString() {
        return "periodicite{" + "date_deb=" + date_deb + ", date_fin=" + date_fin + ", frequancy='" + frequancy + '\'' + '}';
    }

    public LocalDateTime getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(LocalDateTime date_deb) {
        this.date_deb = date_deb;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public String getFrequancy() {
        return frequancy;
    }

    public void setFrequancy(String frequancy) {
        this.frequancy = frequancy;
    }
}
