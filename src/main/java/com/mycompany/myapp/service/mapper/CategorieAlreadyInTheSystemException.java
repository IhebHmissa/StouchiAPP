package com.mycompany.myapp.service.mapper;

public class CategorieAlreadyInTheSystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategorieAlreadyInTheSystemException() {
        super(" Categorie Already In TheSystem !");
    }
}
