package main.java.com.codefest.ecommercespringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Category {
    @Id
    private int id;
    private String name;
}