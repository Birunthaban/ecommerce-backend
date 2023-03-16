package com.ecommerce.backend.model;

import javax.persistence.*;

@Entity
@Table(name="Cart")
public class Cart {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;


    
}

