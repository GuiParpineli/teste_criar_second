package com.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "pilot", orphanRemoval = true)
    private List<Lap> laps = new ArrayList<>();


    public Pilot(String name) {
        this.name = name;
    }
}
