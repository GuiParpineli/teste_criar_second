package com.demo.demo.model.dto;

import com.demo.demo.model.Prova;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PilotoDTO {
    List<Prova> provas;
    Map<Integer, Integer> positions;
}
