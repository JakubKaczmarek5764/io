package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "Charity")
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int charity_id;

    public int getCharity_id() {
        return charity_id;
    }
}
