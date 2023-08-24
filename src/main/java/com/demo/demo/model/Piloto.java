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
public class Piloto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "piloto", orphanRemoval = true)
    private List<Volta> voltas = new ArrayList<>();


    public Piloto(String name) {
        this.name = name;
    }
}
