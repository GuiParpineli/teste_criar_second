package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Piloto;
import com.demo.demo.model.Prova;
import com.demo.demo.model.Volta;
import com.demo.demo.model.dto.PilotoDTO;
import com.demo.demo.repository.PilotoRepository;
import com.demo.demo.service.intefaces.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PilotoService implements ICrud<Piloto> {
    private final PilotoRepository repository;

    @Autowired
    public PilotoService(PilotoRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    public ResponseEntity<?> getbyID(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma piloto com id informado");
        return ResponseEntity.ok(repository.findById(id));
    }

    public ResponseEntity<?> getByPilotoName(String name) {
        Piloto piloto = repository.findByName(name);
        List<Prova> provas = piloto.getVoltas().stream()
                .map(Volta::getProva)
                .distinct()
                .toList();

        Map<Integer, Integer> positions = pilotPlacements(piloto);

        return ResponseEntity.ok(new PilotoDTO(provas, positions));
    }

    public Map<Integer, Integer> pilotPlacements(Piloto piloto) {
        Map<Integer, Integer> places = new HashMap<>();

        List<Volta> voltas = piloto.getVoltas();

        Map<Prova, List<Volta>> groupedByProva = voltas.stream()
                .collect(Collectors.groupingBy(Volta::getProva));

        for (List<Volta> voltasProva : groupedByProva.values()) {
            List<Volta> sortedVoltas = voltasProva.stream()
                    .sorted(Comparator.comparing(Volta::getTempoTotal))
                    .toList();
            //+1 porque contamos os lugares a partir de 1
            int place = sortedVoltas.indexOf(piloto) + 1;

            if (place <= 3) {
                places.put(place, places.getOrDefault(place, 0) + 1);
            }
        }
        return places;
    }

    @Override
    public ResponseEntity<?> save(Piloto input) throws SaveErrorException {
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Piloto nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    @Override
    public ResponseEntity<?> update(Piloto input) throws SaveErrorException, ResourceNotFoundException {
        if (repository.findById(input.getId()).isEmpty())
            throw new ResourceNotFoundException("Nenhuma piloto com id informado");
        try {
            repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Piloto nao atualizada" + e.getMessage());
        }
        return ResponseEntity.ok("Atulizado com sucesso");
    }

    @Override
    public ResponseEntity<?> delete(Integer id) throws ResourceNotFoundException {
        if (repository.findById(id).isEmpty())
            throw new ResourceNotFoundException("Nenhuma piloto com id informado");
        repository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }
}
