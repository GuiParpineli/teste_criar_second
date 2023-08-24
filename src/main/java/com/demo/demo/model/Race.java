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
public class Race {
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
    @OneToMany(mappedBy = "race", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Lap> laps = new ArrayList<>(this.totalVoltas);

    public Race(LocalDateTime dataProva, String local,
                Integer totalVoltas) {
        this.dataProva = dataProva;
        this.local = local;
        this.totalVoltas = totalVoltas;
    }

    public Race(Race race) {
        this.id = race.id;
        this.dataProva = race.getDataProva();
        this.local = race.getLocal();
        this.totalVoltas = race.getTotalVoltas();
        this.podio = race.getPodio();
        this.duracao = race.getDuracao();
        this.qtdPilotos = race.getQtdPilotos();
        this.laps = race.getLaps();
    }
}
