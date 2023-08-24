package com.demo.demo.service;

import com.demo.demo.exceptions.ResourceNotFoundException;
import com.demo.demo.exceptions.SaveErrorException;
import com.demo.demo.model.Pilot;
import com.demo.demo.model.Race;
import com.demo.demo.model.Lap;
import com.demo.demo.model.dto.PilotDTO;
import com.demo.demo.repository.PilotoRepository;
import com.demo.demo.service.intefaces.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PilotService implements ICrud<Pilot> {
    private final PilotoRepository repository;

    @Autowired
    public PilotService(PilotoRepository repository) {
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
        Pilot pilot = repository.findByName(name);
        List<Race> races = pilot.getLaps().stream()
                .map(Lap::getRace)
                .distinct()
                .toList();

        races.forEach(
                a -> {
                    Optional<String> matchedPilot = a.getPodio().stream()
                            .filter(s -> s.contains(pilot.getName()))
                            .findFirst();

                    matchedPilot.ifPresent(s -> a.setPodio(Collections.singletonList(s)));
                }
        );


        Pattern pattern = Pattern.compile("^\\d+");
        Map<Integer, Integer> positions = races.stream()
                .flatMap(podio -> podio.getPodio().stream())
                .map(item -> {
                    Matcher matcher = pattern.matcher(item);
                    return matcher.find() ? Integer.parseInt(matcher.group()) : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        pos -> pos,
                        pos -> 1,
                        Integer::sum
                ));
        return ResponseEntity.ok(new PilotDTO(races, positions));
    }


    @Override
    public ResponseEntity<?> save(Pilot input) throws SaveErrorException {
        try {
            repository.save(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Pilot nao salva" + e.getMessage());
        }
        return ResponseEntity.ok("Salvo com sucesso");
    }

    @Override
    public ResponseEntity<?> update(Pilot input) throws SaveErrorException, ResourceNotFoundException {
        if (repository.findById(input.getId()).isEmpty())
            throw new ResourceNotFoundException("Nenhuma piloto com id informado");
        try {
            repository.saveAndFlush(input);
        } catch (Exception e) {
            throw new SaveErrorException("Erro, Pilot nao atualizada" + e.getMessage());
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
