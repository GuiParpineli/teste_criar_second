package com.demo.demo.model.dto;

import com.demo.demo.model.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RaceDTO {
    private List<Race> races;
    private HashMap<String, LocalTime> bestlaps;
}
