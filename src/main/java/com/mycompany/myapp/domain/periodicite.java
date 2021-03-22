package com.mycompany.myapp.domain;

import java.util.Date;
import org.springframework.data.annotation.Id;

public class periodicite {

    @Id
    private String ID;

    private Date date_deb;
    private Date date_fin;
}
