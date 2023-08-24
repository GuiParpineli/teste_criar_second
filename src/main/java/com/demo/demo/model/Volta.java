package com.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Volta implements Comparable<Volta> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ElementCollection
    private List<LocalTime> tempo;
    private LocalTime tempoTotal;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "piloto_id")
    private Piloto piloto;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prova_id")
    private Prova prova;

    public Volta(Integer id, List<LocalTime> tempo, Piloto piloto, Prova prova) {
        this.id = id;
        this.tempo = tempo;
        this.piloto = piloto;
        this.prova = prova;
        this.tempoTotal = this.tempo.get(0);
        for (int i = 0; i < this.getTempo().size(); i++) {
            for (int j = i + 1; j < this.getTempo().size(); j++) {
                setTempoTotal(
                        this.getTempoTotal()
                                .plusHours(this.getTempo().get(j).getHour())
                                .plusMinutes(this.getTempo().get(j).getMinute())
                                .plusSeconds(this.getTempo().get(j).getSecond())
                                .plusNanos(this.getTempo().get(j).getNano())
                );
            }
        }
    }

    public void setTempoTotal(LocalTime tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    @Override
    public int compareTo(Volta other) {
        return this.getTempoTotal().compareTo(other.getTempoTotal());
    }
}
