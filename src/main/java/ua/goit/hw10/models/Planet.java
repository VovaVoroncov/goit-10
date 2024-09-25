package ua.goit.hw10.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Planet {

    @Id
    private String id;

    @Column
    private String name;
}
