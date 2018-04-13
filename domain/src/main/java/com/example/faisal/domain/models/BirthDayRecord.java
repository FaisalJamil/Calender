/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.domain.models;

import java.io.Serializable;


public class BirthDayRecord implements Serializable {
    public String name;
    public String date;

    public BirthDayRecord with (String name, String date){
        this.name = name;
        this.date = date;
        return this;
    }
}
