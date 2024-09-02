package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Customer2 {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String birthdate;
}
