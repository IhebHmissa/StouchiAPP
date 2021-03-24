package com.mycompany.myapp.service.mapper;

public class UserByLoginDontExiste extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserByLoginDontExiste() {
        super(" No User Found with this user Loin !");
    }
}
