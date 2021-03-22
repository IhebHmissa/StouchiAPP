package com.mycompany.myapp.service.mapper;

public class NoSuchCategorieFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoSuchCategorieFoundException() {
        super(" No Categorie Matched found in the system !");
    }
}
