package com.demo.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Prova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private LocalDateTime dataProva;
    private String local;
    private LocalTime duracao;
    private Integer qtdPilotos;

    @ElementCollection
    private List<String> podio = new ArrayList<>();

    private Integer totalVoltas = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "prova", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Volta> voltas = new ArrayList<>(this.totalVoltas);

    public Prova( LocalDateTime dataProva, String local, Integer qtdPilotos,
                 List<String> podio, Integer totalVoltas) {
        this.dataProva = dataProva;
        this.local = local;
        this.podio = podio;
        this.totalVoltas = totalVoltas;
    }

}
