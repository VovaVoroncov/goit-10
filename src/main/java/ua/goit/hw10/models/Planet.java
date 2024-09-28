package ua.goit.hw10.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "planet")
@Data
public class Planet {

    @Id
    private String id;

    @Column
    private String name;

    @OneToMany(mappedBy = "planet")
    private List<Ticket> tickets;
}
