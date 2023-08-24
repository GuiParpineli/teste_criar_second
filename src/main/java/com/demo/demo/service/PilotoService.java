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

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

        provas.forEach(
                a -> {
                    Optional<String> matchedPilot = a.getPodio().stream()
                            .filter(s -> s.contains(piloto.getName()))
                            .findFirst();

                    matchedPilot.ifPresent(s -> a.setPodio(Collections.singletonList(s)));
                }
        );


        Pattern pattern = Pattern.compile("^\\d+");
        Map<Integer, Integer> positions = provas.stream()
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
        return ResponseEntity.ok(new PilotoDTO(provas, positions));
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
