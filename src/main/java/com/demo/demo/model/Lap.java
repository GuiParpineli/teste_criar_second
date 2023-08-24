package com.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lap implements Comparable<Lap> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ElementCollection
    private List<LocalTime> tempo;
    private LocalTime tempoTotal;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "piloto_id")
    private Pilot pilot;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prova_id")
    private Race race;

    public Lap(Integer id, List<LocalTime> tempo, Pilot pilot, Race race) {
        this.id = id;
        this.tempo = tempo;
        this.pilot = pilot;
        this.race = race;
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
    public int compareTo(Lap other) {
        return this.getTempoTotal().compareTo(other.getTempoTotal());
    }
}
