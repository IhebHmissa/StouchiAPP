package com.mycompany.myapp.domain;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Categories")
public class Categorie {

    @Id
    private String ID;

    private String type;
    private float montant;
    private String nomcatego;
    private String originType;
    private String color;
    private float Minmontant;
    private float Maxmontant;
    private List<Categorie> subcategories;
    private periodicite periodcategorie;

    public Categorie() {}

    public Categorie(
        String ID,
        String originType,
        String type,
        float montant,
        String nomcatego,
        String color,
        float minmontant,
        float maxmontant,
        periodicite periodcategorie
    ) {
        this.ID = ID;
        this.type = type;
        this.montant = montant;
        this.nomcatego = nomcatego;
        this.color = color;
        Minmontant = minmontant;
        Maxmontant = maxmontant;
        this.periodcategorie = periodcategorie;
    }

    public Categorie(String type, String originType, float montant, String nomcatego, String color, periodicite periodcategorie) {
        this.type = type;
        this.montant = montant;
        this.nomcatego = nomcatego;
        this.color = color;
        this.originType = originType;
        this.periodcategorie = periodcategorie;
    }

    public Categorie(String type, String originType, float montant, String nomcatego, String color) {
        this.type = type;
        this.montant = montant;
        this.nomcatego = nomcatego;
        this.originType = originType;
        this.color = color;
    }

    public Categorie(String type, String originType, float montant, String nomcatego, String color, float minmontant, float maxmontant) {
        this.type = type;
        this.montant = montant;
        this.nomcatego = nomcatego;
        this.originType = originType;
        this.color = color;
        Minmontant = minmontant;
        Maxmontant = maxmontant;
    }

    @Override
    public String toString() {
        return (
            "Categorie{" +
            "ID='" +
            ID +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", montant=" +
            montant +
            ", nomcatego='" +
            nomcatego +
            '\'' +
            ", originType='" +
            originType +
            '\'' +
            ", color='" +
            color +
            '\'' +
            ", Minmontant=" +
            Minmontant +
            ", Maxmontant=" +
            Maxmontant +
            '}'
        );
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType1) {
        originType = originType1;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getNomcatego() {
        return nomcatego;
    }

    public void setNomcatego(String nomcatego) {
        this.nomcatego = nomcatego;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getMinmontant() {
        return Minmontant;
    }

    public void setMinmontant(float minmontant) {
        Minmontant = minmontant;
    }

    public float getMaxmontant() {
        return Maxmontant;
    }

    public void setMaxmontant(float maxmontant) {
        Maxmontant = maxmontant;
    }

    public List<Categorie> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Categorie> subcategories) {
        this.subcategories = subcategories;
    }

    public periodicite getPeriodcategorie() {
        return periodcategorie;
    }

    public void setPeriodcategorie(periodicite periodcategorie) {
        this.periodcategorie = periodcategorie;
    }
}
