package org.example;

import jakarta.persistence.*;

@Entity
@Table(name = "Resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int resource_id;

    public int getResource_id() {
        return resource_id;
    }
}
