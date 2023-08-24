package com.demo.demo.model.dto;

import com.demo.demo.model.Race;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PilotDTO {
    List<Race> races;
    Map<Integer, Integer> positions;
}
