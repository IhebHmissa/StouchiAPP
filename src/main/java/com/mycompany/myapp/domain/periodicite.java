package com.mycompany.myapp.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "periodicite")
public class periodicite {

    @Id
    private String ID;

    private LocalDateTime date_deb;
    private LocalDateTime date_fin;
    private String frequancy;
    private float fixedMontant;
    private long numberleft;

    public periodicite() {}

    private long[] calculDurations(LocalDateTime DDB, LocalDateTime DDF) {
        Period period = Period.between(DDB.toLocalDate(), DDF.toLocalDate());
        Duration duration = Duration.between(DDB.toLocalTime(), DDF.toLocalTime());

        if (duration.isNegative()) {
            period = period.minusDays(1);
        }
        return new long[] { period.getDays(), period.getMonths(), period.getYears() };
    }

    public periodicite(LocalDateTime date_deb, LocalDateTime date_fin, String frequancy, float fixedMontant, Integer numberleft) {
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.frequancy = frequancy;
        this.fixedMontant = fixedMontant;
        if (frequancy.equals("jour")) this.numberleft = calculDurations(date_deb, date_fin)[0];
        if (frequancy.equals("semaine")) this.numberleft = calculDurations(date_deb, date_fin)[0] / 7;
        if (frequancy.equals("2semaine")) this.numberleft = calculDurations(date_deb, date_fin)[0] / 14;
        if (frequancy.equals("mois")) this.numberleft = calculDurations(date_deb, date_fin)[1];
        if (frequancy.equals("trimestre")) this.numberleft = calculDurations(date_deb, date_fin)[1] / 4;
        if (frequancy.equals("semestre")) this.numberleft = calculDurations(date_deb, date_fin)[1] / 6;
        if (frequancy.equals("annee")) this.numberleft = calculDurations(date_deb, date_fin)[2]; else this.numberleft = 0;
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

    public float getFixedMontant() {
        return fixedMontant;
    }

    public void setFixedMontant(float fixedMontant) {
        this.fixedMontant = fixedMontant;
    }

    public long getNumberleft() {
        return numberleft;
    }

    public void setNumberleft(long numberleft) {
        this.numberleft = numberleft;
    }
}
