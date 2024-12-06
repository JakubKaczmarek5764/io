package org.example;

import jakarta.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Access(AccessType.FIELD)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    public User(){
    }

    public int getUser_id() {
        return user_id;
    }
}
