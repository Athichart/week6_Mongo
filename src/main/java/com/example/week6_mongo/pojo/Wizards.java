package com.example.week6_mongo.pojo;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Component
public class Wizards implements Serializable {
    public ArrayList<Wizard> model;

    public Wizards(){
        model = new ArrayList<>();
    }
}
